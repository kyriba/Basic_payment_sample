package sample;

import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sample.model.GetStatusResponseModel;
import sample.model.GetTokenResponseModel;
import sample.model.RunTaskResponseModel;
import sample.model.UploadDataResponseModel;

import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Service
public class Application {

    private String accessToken;

    @Value("${access.token.url}")
    private String ACCESS_TOKEN_URL;
    @Value("${client.id}")
    private String CLIENT_ID;
    @Value("${client.secret}")
    private String CLIENT_SECRET;
    @Value("${base.url}")
    private String BASE_URL;
    @Value("${templateRef.import}")
    private String IMPORT_TEMPLATE_REFERENCE;
    @Value("${templateRef.export}")
    private String EXPORT_TEMPLATE_REFERENCE;

    private final JSON json = new JSON();

    private void refreshToken() throws IOException {

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&scope=");
        Request request = new Request.Builder()
                .url(ACCESS_TOKEN_URL)
                .method("POST", body)
                .addHeader("Authorization", "Basic " + base64EncodedBasicAuthentication())
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        if (response != null) {
            GetTokenResponseModel getTokenResponseModel =
                    json.deserialize(response.body().string(), GetTokenResponseModel.class);
            accessToken = getTokenResponseModel.getAccessToken();
        } else {
            System.out.println("Cannot get access token. Please, check your credentials and access token url.\n");
        }
    }

    private String base64EncodedBasicAuthentication() {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    private UploadDataResponseModel uploadNewData(String sendPaymentCommand) throws IOException {
        String requestBody = sendPaymentCommand
                .substring(0, sendPaymentCommand.length() - 1)
                .replace("sendPayment(", "")
                .trim();
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(BASE_URL + "/v1/data?fileName=payment.txt")
                .method("POST", body)
                .addHeader("Content-Type", "text/plain;charset=utf-8")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null) {
            if (response.code() == 401) {
                refreshToken();
                return uploadNewData(sendPaymentCommand);
            } else {
                return json.deserialize(response.body().string(), UploadDataResponseModel.class);
            }
        } else {
            return null;
        }
    }

    private RunTaskResponseModel createTaskToRunSpecificProcessTemplate(String fileId) throws IOException {
        String url = "/v1/process-templates/";
        if (fileId != null) {
            url = url.concat(IMPORT_TEMPLATE_REFERENCE).concat("/run?fileIds=").concat(fileId);
        } else {
            url = url.concat(EXPORT_TEMPLATE_REFERENCE).concat("/run");
        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null) {
            if (response.code() == 401) {
                refreshToken();
                return createTaskToRunSpecificProcessTemplate(fileId);
            } else {
                String result = response.body().string();
                return json.deserialize(result.substring(1, result.length() - 1).trim(), RunTaskResponseModel.class);
            }
        } else {
            return null;
        }
    }

    private GetStatusResponseModel getTaskStatus(String taskUuid) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + "/v1/process-templates/" + taskUuid + "/status")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null) {
            if (response.code() == 401) {
                refreshToken();
                return getTaskStatus(taskUuid);
            } else {
                return json.deserialize(response.body().string(), GetStatusResponseModel.class);
            }
        } else {
            return null;
        }
    }

    private String getPayments(String taskId) throws IOException {
        String url = "/v1/process-templates/" + EXPORT_TEMPLATE_REFERENCE + "/files?taskId=" + taskId;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null) {
            if (response.code() == 401) {
                refreshToken();
                return getPayments(taskId);
            } else {
                return response.body().string();
            }
        } else {
            return null;
        }
    }

    public void runApplication() throws IOException {
        refreshToken();
        Scanner sc = new Scanner(System.in);
        UploadDataResponseModel uploadData;
        RunTaskResponseModel runTaskResponseModelImport = null;
        RunTaskResponseModel runTaskResponseModelExport;
        String input;
        do {
            System.out.println("\nEnter command you want to execute. To stop the application enter \"exit\". To learn about available functions enter \"-help\".");
            input = sc.nextLine();
            input = input.trim();
            if (input.equals("-help")) {
                System.out.println(" - sendPayment(paymentId,paymentDueDate,paymentAmount,paymentBAN,disbursementBankAccount,paymentDescription,payeeName,paymentMethod,paymentType,currencyCode) => Function that includes parameters to integrate payment");
                System.out.println(" - getStatus => See the status of the last sendPayment");
                System.out.println(" - getPayments => Get a list of payments\n");
            } else if (input.equals("exit")) {
                System.exit(1);
            } else if (input.contains("sendPayment(")) {
                uploadData = uploadNewData(input);
                if (uploadData != null) {
                    System.out.println("New data was successfully uploaded:\n" + uploadData);
                    runTaskResponseModelImport = createTaskToRunSpecificProcessTemplate(uploadData.getFileId());
                    System.out.println("\n" + runTaskResponseModelImport);
                } else {
                    System.out.println("New data wasn't uploaded.\n");
                }
            } else if (input.equals("getStatus")) {
                if (runTaskResponseModelImport != null) {
                    GetStatusResponseModel statusResult = getTaskStatus(runTaskResponseModelImport.getTaskId());
                    System.out.println(statusResult);
                } else {
                    System.out.println("Cannot get status of the unidentified task");
                }
            } else if (input.equals("getPayments")) {
                runTaskResponseModelExport = createTaskToRunSpecificProcessTemplate(null);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                if (runTaskResponseModelExport != null) {
                    String payments = getPayments(runTaskResponseModelExport.getTaskId());
                    System.out.println(payments);
                } else {
                    System.out.println("Cannot get payments of the unidentified template");
                }
            } else {
                System.out.println("There is no such command. For more information on a specific command, type \"-help\"");
            }
        } while (true);
    }
}
