# StreamsApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addEvents**](StreamsApi.md#addEvents) | **PUT** /products/{productInstanceId}/streams/addEvents | Add events to the streams processing stack
[**getStreamsResults**](StreamsApi.md#getStreamsResults) | **GET** /products/{productInstanceId}/streams/results | Retrieves the last streams calculation results
[**runStreams**](StreamsApi.md#runStreams) | **PUT** /products/{productInstanceId}/streams/run | Calculates all streams according to the events sent


<a name="addEvents"></a>
# **addEvents**
> java.util.List&lt;Object&gt; addEvents(productInstanceId, body)

Add events to the streams processing stack



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.StreamsApi;


StreamsApi apiInstance = new StreamsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
java.util.List<Object> body = Arrays.asList(new java.util.List<Object>()); // java.util.List<Object> | 
try {
    java.util.List<Object> result = apiInstance.addEvents(productInstanceId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StreamsApi#addEvents");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **body** | **java.util.List&lt;Object&gt;**|  | [optional]

### Return type

**java.util.List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getStreamsResults"></a>
# **getStreamsResults**
> String getStreamsResults(productInstanceId)

Retrieves the last streams calculation results



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.StreamsApi;


StreamsApi apiInstance = new StreamsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    String result = apiInstance.getStreamsResults(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StreamsApi#getStreamsResults");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="runStreams"></a>
# **runStreams**
> java.util.List&lt;Object&gt; runStreams(productInstanceId, streamId, body)

Calculates all streams according to the events sent



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.StreamsApi;


StreamsApi apiInstance = new StreamsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
String streamId = "streamId_example"; // String | 
java.util.List<Object> body = Arrays.asList(new java.util.List<Object>()); // java.util.List<Object> | 
try {
    java.util.List<Object> result = apiInstance.runStreams(productInstanceId, streamId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StreamsApi#runStreams");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **streamId** | **String**|  | [optional]
 **body** | **java.util.List&lt;Object&gt;**|  | [optional]

### Return type

**java.util.List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

