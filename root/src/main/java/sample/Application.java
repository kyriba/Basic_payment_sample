package sample;

import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sample.model.GetStatusResponseModel;
import sample.model.GetTokenResponseModel;
import sample.model.RunTaskResponseModel;
import sample.model.UploadDataResponseModel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
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
    private RunTaskResponseModel runTaskResponseModelImport;

    public void refreshToken() throws IOException {

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
            System.out.println("Cannot get access token. Please, check your credentials and access token url.");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.exit(1);
        }
    }

    private String base64EncodedBasicAuthentication() {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public void sendPayment(List<String> paymentIDs, List<Date> paymentDueDates, List<Double> paymentAmounts,
                             List<Long> paymentBANs, List<Long> disbursementBankAccountNumbers,
                             List<String> paymentDescriptions, List<String> payeeNames, List<String> paymentMethods,
                             List<String> paymentTypes, List<String> currencyCodes) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder payload = new StringBuilder();
        for (int i = 0; i < paymentIDs.size(); i++) {
            payload
                    .append(paymentIDs.get(i)).append(',')
                    .append(dateFormat.format(paymentDueDates.get(i))).append(',')
                    .append(paymentAmounts.get(i)).append(',')
                    .append(paymentBANs.get(i)).append(',')
                    .append(disbursementBankAccountNumbers.get(i)).append(',')
                    .append(paymentDescriptions.get(i)).append(',')
                    .append(payeeNames.get(i)).append(',')
                    .append(paymentMethods.get(i)).append(',')
                    .append(paymentTypes.get(i)).append(',')
                    .append(currencyCodes.get(i));
            if (i != paymentIDs.size() - 1) {
                payload.append(System.lineSeparator());
            }
        }
        UploadDataResponseModel uploadData = uploadNewData(String.valueOf(payload));
        if (uploadData != null) {
            System.out.println(System.lineSeparator() + "New data was successfully uploaded:" +
                    System.lineSeparator() + uploadData);
            runTaskResponseModelImport = createTaskToRunSpecificProcessTemplate(uploadData.getFileId());
            System.out.println(runTaskResponseModelImport);
        } else {
            System.out.println(System.lineSeparator() + "New data wasn't uploaded." + System.lineSeparator());
        }

    }

    private UploadDataResponseModel uploadNewData(String requestBody) throws IOException {
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
                return uploadNewData(requestBody);
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

    public void getStatus() throws IOException {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        String taskUuid;
        if (runTaskResponseModelImport != null) {
            taskUuid = runTaskResponseModelImport.getTaskId();
        } else {
            System.out.println("Cannot get status of the unidentified task");
            return;
        }
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
                getStatus();
            } else {
                GetStatusResponseModel getStatusResponseModel =
                        json.deserialize(response.body().string(), GetStatusResponseModel.class);
                System.out.println(System.lineSeparator() + getStatusResponseModel);
            }
        } else {
            System.out.println("Request was not successful");
        }
    }

    public void getPayments() throws IOException {
        RunTaskResponseModel runTaskResponseModelExport = createTaskToRunSpecificProcessTemplate(null);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        String taskId;
        if (runTaskResponseModelExport != null) {
            taskId = runTaskResponseModelExport.getTaskId();
        } else {
            System.out.println("Cannot get payments of the unidentified template");
            return;
        }
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
                getPayments();
            } else {
                System.out.println(System.lineSeparator() + response.body().string());
            }
        } else {
            System.out.println("Request was not successful");
        }
    }
}
