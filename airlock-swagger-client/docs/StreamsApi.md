# StreamsApi

All URIs are relative to *http://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addEvents**](StreamsApi.md#addEvents) | **PUT** /products/{productInstanceId}/streams/addEvents | Add events to the streams processing stack
[**getStreamsResults**](StreamsApi.md#getStreamsResults) | **GET** /products/{productInstanceId}/streams/results | Retrieves the last streams calculation results
[**runStreams**](StreamsApi.md#runStreams) | **PUT** /products/{productInstanceId}/streams/run | Calculates all streams according to the events sent


<a name="addEvents"></a>
# **addEvents**
> String addEvents(productInstanceId, ERROR_UNKNOWN)

Add events to the streams processing stack

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.StreamsApi;


StreamsApi apiInstance = new StreamsApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
List<Object> ERROR_UNKNOWN = Arrays.asList(new List()); // List<Object> | 
try {
    String result = apiInstance.addEvents(productInstanceId, ERROR_UNKNOWN);
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
 **ERROR_UNKNOWN** | [**List&lt;Object&gt;**](List.md)|  | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

<a name="getStreamsResults"></a>
# **getStreamsResults**
> Map&lt;String, Object&gt; getStreamsResults(productInstanceId)

Retrieves the last streams calculation results

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.StreamsApi;


StreamsApi apiInstance = new StreamsApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    Map<String, Object> result = apiInstance.getStreamsResults(productInstanceId);
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

**Map&lt;String, Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="runStreams"></a>
# **runStreams**
> String runStreams(productInstanceId, streamId, ERROR_UNKNOWN)

Calculates all streams according to the events sent

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.StreamsApi;


StreamsApi apiInstance = new StreamsApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String streamId = "streamId_example"; // String | 
List<Object> ERROR_UNKNOWN = Arrays.asList(new List()); // List<Object> | 
try {
    String result = apiInstance.runStreams(productInstanceId, streamId, ERROR_UNKNOWN);
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
 **ERROR_UNKNOWN** | [**List&lt;Object&gt;**](List.md)|  | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

