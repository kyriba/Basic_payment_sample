package sample;

import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sample.model.Payment;
import sample.model.PaymentStatus;
import sample.model.TaskStatus;
import sample.model.response.GetStatusResponseModel;
import sample.model.response.GetTokenResponseModel;
import sample.model.response.RunTaskResponseModel;
import sample.model.response.UploadDataResponseModel;

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

    public TaskStatus singlePayment(String paymentID, Date paymentDueDate, Double paymentAmount, Long paymentBAN,
                                    Long disbursementBankAccountNumber, String paymentDescription, String payeeName,
                                    String paymentMethod, String paymentType, String currencyCode) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String payload = paymentID + ',' +
                dateFormat.format(paymentDueDate) + ',' +
                paymentAmount + ',' +
                paymentBAN + ',' +
                disbursementBankAccountNumber + ',' +
                paymentDescription + ',' +
                payeeName + ',' +
                paymentMethod + ',' +
                paymentType + ',' +
                currencyCode;

        UploadDataResponseModel uploadData = uploadNewData(payload);
        if (uploadData != null) {
            runTaskResponseModelImport = createTaskToRunSpecificProcessTemplate(uploadData.getFileId());
        } else {
            System.out.println(System.lineSeparator() + "New data wasn't uploaded." + System.lineSeparator());
            return new TaskStatus();
        }

        if (runTaskResponseModelImport != null) {
            TaskStatus taskStatus = new TaskStatus();
            taskStatus.setTaskId(runTaskResponseModelImport.getTaskId());
            taskStatus.setFileId(runTaskResponseModelImport.getFileId());
            GetStatusResponseModel getStatusResponseModel = getStatus(runTaskResponseModelImport.getTaskId());
            if (getStatusResponseModel != null) {
                taskStatus.setStatus(getStatusResponseModel.getStatus());
            }
            return taskStatus;
        } else {
            System.out.println(System.lineSeparator() + "New task wasn't created." + System.lineSeparator());
            return new TaskStatus();
        }
    }

    public TaskStatus singlePayment(Payment payment) throws IOException {
        if (payment != null) {
            String paymentID = payment.getPaymentID();
            Date paymentDueDate = payment.getPaymentDueDate();
            Double paymentAmount = payment.getPaymentAmount();
            Long paymentBAN = payment.getPaymentBAN();
            Long disbursementBankAccountNumber = payment.getDisbursementBankAccountNumber();
            String paymentDescription = payment.getPaymentDescription();
            String payeeName = payment.getPayeeName();
            String paymentMethod = payment.getPaymentMethod();
            String paymentType = payment.getPaymentType();
            String currencyCode = payment.getCurrencyCode();
            return singlePayment(paymentID, paymentDueDate, paymentAmount, paymentBAN, disbursementBankAccountNumber,
                    paymentDescription, payeeName, paymentMethod, paymentType, currencyCode);
        }
        System.out.println(System.lineSeparator() + "Cannot upload the payment with no data." + System.lineSeparator());
        return new TaskStatus();
    }

    public TaskStatus bulkPayment(List<Payment> payments) throws IOException {
        if (payments == null || payments.isEmpty()) {
            System.out.println(System.lineSeparator() + "Cannot upload the payment with no data." + System.lineSeparator());
            return new TaskStatus();
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder payload = new StringBuilder();
        for (int i = 0; i < payments.size(); i++) {
            Payment payment = payments.get(i);
            if (payment != null) {
                payload
                        .append(payment.getPaymentID()).append(',')
                        .append(dateFormat.format(payment.getPaymentDueDate())).append(',')
                        .append(payment.getPaymentAmount()).append(',')
                        .append(payment.getPaymentBAN()).append(',')
                        .append(payment.getDisbursementBankAccountNumber()).append(',')
                        .append(payment.getPaymentDescription()).append(',')
                        .append(payment.getPayeeName()).append(',')
                        .append(payment.getPaymentMethod()).append(',')
                        .append(payment.getPaymentType()).append(',')
                        .append(payment.getCurrencyCode());
                if (i != payments.size() - 1) {
                    payload.append(System.lineSeparator());
                }
            }
        }

        UploadDataResponseModel uploadData = uploadNewData(String.valueOf(payload));
        if (uploadData != null) {
            runTaskResponseModelImport = createTaskToRunSpecificProcessTemplate(uploadData.getFileId());
        } else {
            System.out.println(System.lineSeparator() + "New data wasn't uploaded." + System.lineSeparator());
            return new TaskStatus();
        }

        if (runTaskResponseModelImport != null) {
            TaskStatus taskStatus = new TaskStatus();
            taskStatus.setTaskId(runTaskResponseModelImport.getTaskId());
            taskStatus.setFileId(runTaskResponseModelImport.getFileId());
            GetStatusResponseModel getStatusResponseModel = getStatus(runTaskResponseModelImport.getTaskId());
            if (getStatusResponseModel != null) {
                taskStatus.setStatus(getStatusResponseModel.getStatus());
            }
            return taskStatus;
        } else {
            System.out.println(System.lineSeparator() + "New task wasn't created." + System.lineSeparator());
            return new TaskStatus();
        }
    }

    public List<PaymentStatus> getPaymentStatus() {
        return null;
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

    private GetStatusResponseModel getStatus(String taskUuid) throws IOException {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        if (taskUuid == null) {
            System.out.println("Cannot get status of the unidentified task");
            return null;
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
                getStatus(taskUuid);
            } else {
                return json.deserialize(response.body().string(), GetStatusResponseModel.class);
            }
        } else {
            System.out.println("Request was not successful");
            return null;
        }
        return null;
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
