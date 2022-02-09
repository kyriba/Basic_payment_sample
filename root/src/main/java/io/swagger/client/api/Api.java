package io.swagger.client.api;

import com.google.gson.reflect.TypeToken;
import io.swagger.client.*;
import java.lang.String;
import io.swagger.client.model.UploadDataResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class Api {

    @Value("${templateRef.export}")
    private String exportTemplateRef;

    @Value("${templateRef.import}")
    private String importTemplateRef;

    public ApiClient apiClient;

    public Api(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }


    /**
     * Build call for GETStatus
     *
     * @param taskUuid                The UUID of the task. (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call GETStatusCall(String taskUuid, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/process-templates/{taskUuid}/status"
                .replaceAll("\\{" + "taskUuid" + "\\}", apiClient.escapeString(taskUuid));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[]{"OAuth2ClientCredentials"};
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call GETStatusValidateBeforeCall(String taskUuid, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'taskUuid' is set
        if (taskUuid == null) {
            throw new ApiException("Missing the required parameter 'taskUuid' when calling GETStatus(Async)");
        }


        com.squareup.okhttp.Call call = GETStatusCall(taskUuid, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get the status of the specific task identified by the uuid.
     *
     * @param taskUuid The UUID of the task. (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String GETStatus(String taskUuid) throws ApiException {
        ApiResponse<String> resp = GETStatusWithHttpInfo(taskUuid);
        return resp.getData();
    }

    /**
     * Get the status of the specific task identified by the uuid.
     *
     * @param taskUuid The UUID of the task. (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<String> GETStatusWithHttpInfo(String taskUuid) throws ApiException {
        com.squareup.okhttp.Call call = GETStatusValidateBeforeCall(taskUuid, null, null);
        Type localVarReturnType = new TypeToken<String>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get the status of the specific task identified by the uuid. (asynchronously)
     *
     * @param taskUuid The UUID of the task. (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call GETStatusAsync(String taskUuid, final ApiCallback<String> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = GETStatusValidateBeforeCall(taskUuid, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<String>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for GETPayments
     *
     * @param taskId                  The UUID of the task that has the result file. (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call GETPaymentsCall(String taskId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/process-templates/{templateRef}/files?taskId={taskId}"
                .replaceAll("\\{" + "templateRef" + "\\}", apiClient.escapeString(exportTemplateRef))
                .replaceAll("\\{" + "taskId" + "\\}", apiClient.escapeString(taskId));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[]{"OAuth2ClientCredentials"};
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call GETPaymentsValidateBeforeCall(String taskId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'taskId' is set
        if (taskId == null) {
            throw new ApiException("Missing the required parameter 'taskId' when calling GETPayments(Async)");
        }


        com.squareup.okhttp.Call call = GETPaymentsCall(taskId, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get the result file of the specific template identified by template's reference (uuid or code) and task's uuid.
     *
     * @param taskId The UUID of the task that has the result file. (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String GETPayments(String taskId) throws ApiException {
        ApiResponse<String> resp = GETPaymentsWithHttpInfo(taskId);
        return resp.getData();
    }

    /**
     * Get the result file of the specific template identified by template's reference (uuid or code) and task's uuid.
     *
     * @param taskId The UUID of the task that has the result file. (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<String> GETPaymentsWithHttpInfo(String taskId) throws ApiException {
        com.squareup.okhttp.Call call = GETPaymentsValidateBeforeCall(taskId, null, null);
        Type localVarReturnType = new TypeToken<String>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get the result file of the specific template identified by template's reference (uuid or code) and task's uuid. (asynchronously)
     *
     * @param taskId   The UUID of the task that has the result file. (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call GETPaymentsAsync(String taskId, final ApiCallback<String> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = GETPaymentsValidateBeforeCall(taskId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<String>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for uploadNewDataUsingPOST
     *
     * @param requestBody             String (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call uploadNewDataUsingPOSTCall(String requestBody, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = requestBody;

        // create path and map variables
        String localVarPath = "/v1/data?fileName=payment.txt";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "text/plain;charset=utf-8"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[]{"OAuth2ClientCredentials"};
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call uploadNewDataUsingPOSTValidateBeforeCall(String requestBody, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling uploadNewDataUsingPOST(Async)");
        }


        com.squareup.okhttp.Call call = uploadNewDataUsingPOSTCall(requestBody, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Upload new data by payload.
     *
     * @param requestBody String (required)
     * @return UploadDataResponseModel
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public UploadDataResponseModel uploadNewDataUsingPOST(String requestBody) throws ApiException {
        ApiResponse<UploadDataResponseModel> resp = uploadNewDataUsingPOSTWithHttpInfo(requestBody);
        return resp.getData();
    }

    /**
     * Upload new data by payload.
     *
     * @param requestBody String (required)
     * @return ApiResponse&lt;UploadDataResponseModel&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<UploadDataResponseModel> uploadNewDataUsingPOSTWithHttpInfo(String requestBody) throws ApiException {
        com.squareup.okhttp.Call call = uploadNewDataUsingPOSTValidateBeforeCall(requestBody, null, null);
        Type localVarReturnType = new TypeToken<UploadDataResponseModel>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Upload new data by payload. (asynchronously)
     *
     * @param requestBody   String (required)
     * @param callback      The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call uploadNewDataUsingPOSTAsync(String requestBody, final ApiCallback<UploadDataResponseModel> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = uploadNewDataUsingPOSTValidateBeforeCall(requestBody, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<UploadDataResponseModel>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for createTaskToRunPTUsingPOST
     *
     * @param fileId                  String
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call createTaskToRunPTUsingPOSTCall(String fileId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/process-templates/{templateRef}/run";

        if (fileId != null) {
            localVarPath = localVarPath.concat("?fileIds={fileId}")
                    .replaceAll("\\{" + "templateRef" + "\\}", apiClient.escapeString(importTemplateRef))
                    .replaceAll("\\{" + "fileId" + "\\}", apiClient.escapeString(fileId));
        } else {
            localVarPath = localVarPath
                    .replaceAll("\\{" + "templateRef" + "\\}", apiClient.escapeString(exportTemplateRef));
        }

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[]{"OAuth2ClientCredentials"};
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call createTaskToRunPTUsingPOSTValidateBeforeCall(String fileId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        com.squareup.okhttp.Call call = createTaskToRunPTUsingPOSTCall(fileId, progressListener, progressRequestListener);
        return call;

    }

    /**
     *  Create a task to run a specific process templates identified by reference (uuid or code).
     *
     * @param fileId String
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String createTaskToRunPTUsingPOST(String fileId) throws ApiException {
        ApiResponse<String> resp = createTaskToRunPTUsingPOSTWithHttpInfo(fileId);
        return resp.getData();
    }

    /**
     * Create a task to run a specific process templates identified by reference (uuid or code).
     *
     * @param fileId String
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<String> createTaskToRunPTUsingPOSTWithHttpInfo(String fileId) throws ApiException {
        com.squareup.okhttp.Call call = createTaskToRunPTUsingPOSTValidateBeforeCall(fileId, null, null);
        Type localVarReturnType = new TypeToken<String>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Create a task to run a specific process templates identified by reference (uuid or code). (asynchronously)
     *
     * @param fileId     String
     * @param callback   The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call createTaskToRunPTUsingPOSTAsync(String fileId, final ApiCallback<String> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = createTaskToRunPTUsingPOSTValidateBeforeCall(fileId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<String>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
