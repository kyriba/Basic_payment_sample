package io.swagger.client.service.impl;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.JSON;
import io.swagger.client.api.Api;
import io.swagger.client.exception.InvalidTokenException;
import io.swagger.client.model.RunTaskResponseModel;
import io.swagger.client.model.UploadDataResponseModel;
import io.swagger.client.service.ApiService;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Service
public class ApiServiceImpl implements ApiService {

    @Value("${access.token.url}")
    public String ACCESS_TOKEN_URL;
    @Value("${client.id}")
    public String CLIENT_ID;
    @Value("${client.secret}")
    public String CLIENT_SECRET;

    private final Api api;
    private final JSON json = new JSON();

    public ApiServiceImpl(Api api) {
        this.api = api;
    }

    @PostConstruct
    private void postConstruct() {
        refreshToken();
    }

    private void refreshToken() {

        try {
            OAuthClient client = new OAuthClient(new URLConnectionClient());

            OAuthClientRequest request =
                    OAuthClientRequest.tokenLocation(ACCESS_TOKEN_URL)
                            .setGrantType(GrantType.CLIENT_CREDENTIALS)
                            .setClientId(CLIENT_ID)
                            .setClientSecret(CLIENT_SECRET)
                            .buildQueryMessage();
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "Basic " + base64EncodedBasicAuthentication());

            OAuthAccessTokenResponse token = client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class);
            String accessToken = token.getAccessToken();
            ApiClient defaultClient = api.getApiClient();
            io.swagger.client.auth.OAuth OAuth2ClientCredentials = (io.swagger.client.auth.OAuth) defaultClient.getAuthentication("OAuth2ClientCredentials");
            OAuth2ClientCredentials.setAccessToken(accessToken);

        } catch (Exception exn) {
            exn.printStackTrace();
        }
    }

    private String base64EncodedBasicAuthentication() {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    private UploadDataResponseModel uploadNewData(String sendPaymentCommand) {
        String requestBody = sendPaymentCommand
                .substring(0, sendPaymentCommand.length() - 1)
                .replace("sendPayment(", "")
                .trim();
        UploadDataResponseModel responseModel = null;
        try {
            responseModel = api.uploadNewDataUsingPOST(requestBody);
        } catch (ApiException e) {
            if (e.getResponseBody() != null && e.getResponseBody().contains("invalid_token")) {
                refreshToken();
                uploadNewData(requestBody);
            } else {
                System.err.println(e.getResponseBody());
            }
        }
        return responseModel;
    }

    private RunTaskResponseModel createTaskToRunSpecificProcessTemplate(String fileId) {
        String result = null;
        try {
            result = api.createTaskToRunPTUsingPOST(fileId);
        } catch (ApiException e) {
            if (e.getResponseBody() != null && e.getResponseBody().contains("invalid_token")) {
                refreshToken();
                createTaskToRunSpecificProcessTemplate(fileId);
            } else {
                System.err.println(e.getResponseBody());
            }
        }
        if (result != null) {
            result = result.substring(1, result.length() - 1).trim();
        }
        return json.deserialize(result, RunTaskResponseModel.class);
    }

    private String getTaskStatus(String taskUuid) {
        String response = null;
        try {
            response = api.GETStatus(taskUuid);
        } catch (ApiException e) {
            if (e.getResponseBody() != null && e.getResponseBody().contains("invalid_token")) {
                refreshToken();
                getTaskStatus(taskUuid);
            } else {
                System.err.println(e.getResponseBody());
            }
        }
        return response;
    }

    private String getPayments(String taskId) {
        String result = null;
        try {
            result = api.GETPayments(taskId);
        } catch (ApiException e) {
            if (e.getResponseBody() != null && e.getResponseBody().contains("invalid_token")) {
                refreshToken();
                getPayments(taskId);
            } else {
                System.err.println(e.getResponseBody());
            }
        }
        return result;
    }

    @Override
    public void runApplication() {
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
                break;
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
                    String statusResult = getTaskStatus(runTaskResponseModelImport.getTaskId());
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
