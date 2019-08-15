# NotificationApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteVasSubscriptions**](NotificationApi.md#deleteVasSubscriptions) | **DELETE** /subscriptions/{id} | Call to VAS service to delete subscription.
[**notificationHandler**](NotificationApi.md#notificationHandler) | **POST** /subscriptions/notify | Handles update notification for Airlock Rest API shared context from VAS
[**vasSubscriptions**](NotificationApi.md#vasSubscriptions) | **POST** /subscriptions | Call to VAS service to create a new subscription to receive events or notifications about property updates.


<a name="deleteVasSubscriptions"></a>
# **deleteVasSubscriptions**
> deleteVasSubscriptions(id)

Call to VAS service to delete subscription.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.NotificationApi;


NotificationApi apiInstance = new NotificationApi();
String id = "id_example"; // String | 
try {
    apiInstance.deleteVasSubscriptions(id);
} catch (ApiException e) {
    System.err.println("Exception when calling NotificationApi#deleteVasSubscriptions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: text/plain
 - **Accept**: Not defined

<a name="notificationHandler"></a>
# **notificationHandler**
> notificationHandler(body)

Handles update notification for Airlock Rest API shared context from VAS



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.NotificationApi;


NotificationApi apiInstance = new NotificationApi();
java.util.List<java.util.Map<String, Object>> body = Arrays.asList(new java.util.Map()); // java.util.List<java.util.Map<String, Object>> | 
try {
    apiInstance.notificationHandler(body);
} catch (ApiException e) {
    System.err.println("Exception when calling NotificationApi#notificationHandler");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**java.util.List&lt;java.util.Map&lt;String, Object&gt;&gt;**](java.util.Map.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="vasSubscriptions"></a>
# **vasSubscriptions**
> vasSubscriptions(body)

Call to VAS service to create a new subscription to receive events or notifications about property updates.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.NotificationApi;


NotificationApi apiInstance = new NotificationApi();
String body = "body_example"; // String | 
try {
    apiInstance.vasSubscriptions(body);
} catch (ApiException e) {
    System.err.println("Exception when calling NotificationApi#vasSubscriptions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: text/plain
 - **Accept**: Not defined

