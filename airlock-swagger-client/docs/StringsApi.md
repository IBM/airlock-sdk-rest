# StringsApi

All URIs are relative to *http://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllStrings**](StringsApi.md#getAllStrings) | **GET** /products/{productInstanceId}/strings | Retrieves all strings translated based on the locale of the application as specified in the pull function. Placeholders will not be replaced with any values and will be returned in their original form.
[**getStringByKey**](StringsApi.md#getStringByKey) | **PUT** /products/{productInstanceId}/strings/{key} | Retrieves the translated string with the specified key based on the locale of the application as specified in the pull function.


<a name="getAllStrings"></a>
# **getAllStrings**
> Map&lt;String, Object&gt; getAllStrings(productInstanceId)

Retrieves all strings translated based on the locale of the application as specified in the pull function. Placeholders will not be replaced with any values and will be returned in their original form.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.StringsApi;


StringsApi apiInstance = new StringsApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    Map<String, Object> result = apiInstance.getAllStrings(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StringsApi#getAllStrings");
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

<a name="getStringByKey"></a>
# **getStringByKey**
> String getStringByKey(productInstanceId, key, ERROR_UNKNOWN)

Retrieves the translated string with the specified key based on the locale of the application as specified in the pull function.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.StringsApi;


StringsApi apiInstance = new StringsApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String key = "key_example"; // String | The key of the string to retrieve.
List<String> ERROR_UNKNOWN = Arrays.asList(new List()); // List<String> | An array of strings to replace any placeholders of the form [[[index]]].
try {
    String result = apiInstance.getStringByKey(productInstanceId, key, ERROR_UNKNOWN);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StringsApi#getStringByKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **key** | **String**| The key of the string to retrieve. |
 **ERROR_UNKNOWN** | [**List&lt;String&gt;**](List.md)| An array of strings to replace any placeholders of the form [[[index]]]. | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

