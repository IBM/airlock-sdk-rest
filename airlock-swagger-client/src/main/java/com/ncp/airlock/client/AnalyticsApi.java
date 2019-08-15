/*
 * 
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.ncp.airlock.client;

import com.ncp.airlock.client.invoker.ApiCallback;
import com.ncp.airlock.client.invoker.ApiClient;
import com.ncp.airlock.client.invoker.ApiException;
import com.ncp.airlock.client.invoker.ApiResponse;
import com.ncp.airlock.client.invoker.Configuration;
import com.ncp.airlock.client.invoker.Pair;
import com.ncp.airlock.client.invoker.ProgressRequestBody;
import com.ncp.airlock.client.invoker.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.ncp.airlock.client.model.AnalyticsData;
import com.ncp.airlock.client.model.FeatureAnalytics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsApi {
    private ApiClient apiClient;

    public AnalyticsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AnalyticsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getAllFieldsForAnalytics
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAllFieldsForAnalyticsCall(String productInstanceId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = new Object();

        // create path and map variables
        String localVarPath = "/products/{productInstanceId}/analytics"
            .replaceAll("\\{" + "productInstanceId" + "\\}", apiClient.escapeString(productInstanceId.toString()));

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
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
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

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAllFieldsForAnalyticsValidateBeforeCall(String productInstanceId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'productInstanceId' is set
        if (productInstanceId == null) {
            throw new ApiException("Missing the required parameter 'productInstanceId' when calling getAllFieldsForAnalytics(Async)");
        }
        

        com.squareup.okhttp.Call call = getAllFieldsForAnalyticsCall(productInstanceId, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieves all analytics data marked to be sent in the Control Center including context fields and feature information.
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @return AnalyticsData
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public AnalyticsData getAllFieldsForAnalytics(String productInstanceId) throws ApiException {
        ApiResponse<AnalyticsData> resp = getAllFieldsForAnalyticsWithHttpInfo(productInstanceId);
        return resp.getData();
    }

    /**
     * Retrieves all analytics data marked to be sent in the Control Center including context fields and feature information.
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @return ApiResponse&lt;AnalyticsData&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<AnalyticsData> getAllFieldsForAnalyticsWithHttpInfo(String productInstanceId) throws ApiException {
        com.squareup.okhttp.Call call = getAllFieldsForAnalyticsValidateBeforeCall(productInstanceId, null, null);
        Type localVarReturnType = new TypeToken<AnalyticsData>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieves all analytics data marked to be sent in the Control Center including context fields and feature information. (asynchronously)
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAllFieldsForAnalyticsAsync(String productInstanceId, final ApiCallback<AnalyticsData> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = getAllFieldsForAnalyticsValidateBeforeCall(productInstanceId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<AnalyticsData>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getFeatureAttributesForAnalytics
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve analytics data for. (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getFeatureAttributesForAnalyticsCall(String productInstanceId, String featureName, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = new Object();

        // create path and map variables
        String localVarPath = "/products/{productInstanceId}/analytics/features/{featureName}"
            .replaceAll("\\{" + "productInstanceId" + "\\}", apiClient.escapeString(productInstanceId.toString()))
            .replaceAll("\\{" + "featureName" + "\\}", apiClient.escapeString(featureName.toString()));

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
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
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

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getFeatureAttributesForAnalyticsValidateBeforeCall(String productInstanceId, String featureName, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'productInstanceId' is set
        if (productInstanceId == null) {
            throw new ApiException("Missing the required parameter 'productInstanceId' when calling getFeatureAttributesForAnalytics(Async)");
        }
        
        // verify the required parameter 'featureName' is set
        if (featureName == null) {
            throw new ApiException("Missing the required parameter 'featureName' when calling getFeatureAttributesForAnalytics(Async)");
        }
        

        com.squareup.okhttp.Call call = getFeatureAttributesForAnalyticsCall(productInstanceId, featureName, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieves all analytics data marked to be sent in the Control Center for the specified feature.
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve analytics data for. (required)
     * @return FeatureAnalytics
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public FeatureAnalytics getFeatureAttributesForAnalytics(String productInstanceId, String featureName) throws ApiException {
        ApiResponse<FeatureAnalytics> resp = getFeatureAttributesForAnalyticsWithHttpInfo(productInstanceId, featureName);
        return resp.getData();
    }

    /**
     * Retrieves all analytics data marked to be sent in the Control Center for the specified feature.
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve analytics data for. (required)
     * @return ApiResponse&lt;FeatureAnalytics&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<FeatureAnalytics> getFeatureAttributesForAnalyticsWithHttpInfo(String productInstanceId, String featureName) throws ApiException {
        com.squareup.okhttp.Call call = getFeatureAttributesForAnalyticsValidateBeforeCall(productInstanceId, featureName, null, null);
        Type localVarReturnType = new TypeToken<FeatureAnalytics>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieves all analytics data marked to be sent in the Control Center for the specified feature. (asynchronously)
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve analytics data for. (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getFeatureAttributesForAnalyticsAsync(String productInstanceId, String featureName, final ApiCallback<FeatureAnalytics> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = getFeatureAttributesForAnalyticsValidateBeforeCall(productInstanceId, featureName, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<FeatureAnalytics>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
