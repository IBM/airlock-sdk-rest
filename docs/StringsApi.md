# StringsApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllStrings**](StringsApi.md#getAllStrings) | **GET** /products/{productInstanceId}/strings | Retrieves all strings translated based on the locale of the application as specified in the pull function. Placeholders will not be replaced with any values and will be returned in their original form.
[**getStringByKey**](StringsApi.md#getStringByKey) | **PUT** /products/{productInstanceId}/strings/{key} | Retrieves the translated string with the specified key based on the locale of the application as specified in the pull function.


<a name="getAllStrings"></a>
# **getAllStrings**
> java.util.Map&lt;String, Object&gt; getAllStrings(productInstanceId)

Retrieves all strings translated based on the locale of the application as specified in the pull function. Placeholders will not be replaced with any values and will be returned in their original form.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.StringsApi;


StringsApi apiInstance = new StringsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    java.util.Map<String, Object> result = apiInstance.getAllStrings(productInstanceId);
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

**java.util.Map&lt;String, Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getStringByKey"></a>
# **getStringByKey**
> String getStringByKey(productInstanceId, key, arguments)

Retrieves the translated string with the specified key based on the locale of the application as specified in the pull function.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.StringsApi;


StringsApi apiInstance = new StringsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
String key = "key_example"; // String | The key of the string to retrieve.
java.util.List<String> arguments = Arrays.asList(new java.util.List<String>()); // java.util.List<String> | An array of strings to replace any placeholders of the form [[[index]]].
try {
    String result = apiInstance.getStringByKey(productInstanceId, key, arguments);
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
 **arguments** | **java.util.List&lt;String&gt;**| An array of strings to replace any placeholders of the form [[[index]]]. | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

