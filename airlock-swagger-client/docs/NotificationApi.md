# NotificationApi

All URIs are relative to *http://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**mergeVASNotificationIntoContext**](NotificationApi.md#mergeVASNotificationIntoContext) | **PUT** /notify | Update notification from VAS into Airlock shared context


<a name="mergeVASNotificationIntoContext"></a>
# **mergeVASNotificationIntoContext**
> mergeVASNotificationIntoContext(ERROR_UNKNOWN)

Update notification from VAS into Airlock shared context

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.NotificationApi;


NotificationApi apiInstance = new NotificationApi();
List<Map<String, Object>> ERROR_UNKNOWN = Arrays.asList(new List()); // List<Map<String, Object>> | 
try {
    apiInstance.mergeVASNotificationIntoContext(ERROR_UNKNOWN);
} catch (ApiException e) {
    System.err.println("Exception when calling NotificationApi#mergeVASNotificationIntoContext");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ERROR_UNKNOWN** | [**List&lt;Map&lt;String, Object&gt;&gt;**](List.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

