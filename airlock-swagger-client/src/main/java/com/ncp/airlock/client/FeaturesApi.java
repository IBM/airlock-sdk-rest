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


import com.ncp.airlock.client.model.Feature;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeaturesApi {
    private ApiClient apiClient;

    public FeaturesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public FeaturesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getFeatureByName
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve. (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getFeatureByNameCall(String productInstanceId, String featureName, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = new Object();

        // create path and map variables
        String localVarPath = "/products/{productInstanceId}/features/{featureName}"
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
    private com.squareup.okhttp.Call getFeatureByNameValidateBeforeCall(String productInstanceId, String featureName, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'productInstanceId' is set
        if (productInstanceId == null) {
            throw new ApiException("Missing the required parameter 'productInstanceId' when calling getFeatureByName(Async)");
        }
        
        // verify the required parameter 'featureName' is set
        if (featureName == null) {
            throw new ApiException("Missing the required parameter 'featureName' when calling getFeatureByName(Async)");
        }
        

        com.squareup.okhttp.Call call = getFeatureByNameCall(productInstanceId, featureName, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieves the feature with the specified name.
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve. (required)
     * @return Feature
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Feature getFeatureByName(String productInstanceId, String featureName) throws ApiException {
        ApiResponse<Feature> resp = getFeatureByNameWithHttpInfo(productInstanceId, featureName);
        return resp.getData();
    }

    /**
     * Retrieves the feature with the specified name.
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve. (required)
     * @return ApiResponse&lt;Feature&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Feature> getFeatureByNameWithHttpInfo(String productInstanceId, String featureName) throws ApiException {
        com.squareup.okhttp.Call call = getFeatureByNameValidateBeforeCall(productInstanceId, featureName, null, null);
        Type localVarReturnType = new TypeToken<Feature>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieves the feature with the specified name. (asynchronously)
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve. (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getFeatureByNameAsync(String productInstanceId, String featureName, final ApiCallback<Feature> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = getFeatureByNameValidateBeforeCall(productInstanceId, featureName, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Feature>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getFeatureChildren
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve the children for. (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getFeatureChildrenCall(String productInstanceId, String featureName, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = new Object();

        // create path and map variables
        String localVarPath = "/products/{productInstanceId}/features/{featureName}/children"
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
    private com.squareup.okhttp.Call getFeatureChildrenValidateBeforeCall(String productInstanceId, String featureName, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'productInstanceId' is set
        if (productInstanceId == null) {
            throw new ApiException("Missing the required parameter 'productInstanceId' when calling getFeatureChildren(Async)");
        }
        
        // verify the required parameter 'featureName' is set
        if (featureName == null) {
            throw new ApiException("Missing the required parameter 'featureName' when calling getFeatureChildren(Async)");
        }
        

        com.squareup.okhttp.Call call = getFeatureChildrenCall(productInstanceId, featureName, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieves the children of the specified feature.
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve the children for. (required)
     * @return List&lt;Feature&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Feature> getFeatureChildren(String productInstanceId, String featureName) throws ApiException {
        ApiResponse<List<Feature>> resp = getFeatureChildrenWithHttpInfo(productInstanceId, featureName);
        return resp.getData();
    }

    /**
     * Retrieves the children of the specified feature.
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve the children for. (required)
     * @return ApiResponse&lt;List&lt;Feature&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Feature>> getFeatureChildrenWithHttpInfo(String productInstanceId, String featureName) throws ApiException {
        com.squareup.okhttp.Call call = getFeatureChildrenValidateBeforeCall(productInstanceId, featureName, null, null);
        Type localVarReturnType = new TypeToken<List<Feature>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieves the children of the specified feature. (asynchronously)
     * 
     * @param productInstanceId The product id as returned by the init function or taken directly from the defaults file. (required)
     * @param featureName The name of the feature to retrieve the children for. (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getFeatureChildrenAsync(String productInstanceId, String featureName, final ApiCallback<List<Feature>> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = getFeatureChildrenValidateBeforeCall(productInstanceId, featureName, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Feature>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
