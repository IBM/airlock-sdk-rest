# AppLifecycleApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**liveness**](AppLifecycleApi.md#liveness) | **GET** /appLifecycle/liveness | 
[**postStart**](AppLifecycleApi.md#postStart) | **GET** /appLifecycle/postStart | 
[**preStop**](AppLifecycleApi.md#preStop) | **GET** /appLifecycle/preStop | 
[**readiness**](AppLifecycleApi.md#readiness) | **GET** /appLifecycle/readiness | 


<a name="liveness"></a>
# **liveness**
> liveness()





### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AppLifecycleApi;


AppLifecycleApi apiInstance = new AppLifecycleApi();
try {
    apiInstance.liveness();
} catch (ApiException e) {
    System.err.println("Exception when calling AppLifecycleApi#liveness");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postStart"></a>
# **postStart**
> postStart()





### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AppLifecycleApi;


AppLifecycleApi apiInstance = new AppLifecycleApi();
try {
    apiInstance.postStart();
} catch (ApiException e) {
    System.err.println("Exception when calling AppLifecycleApi#postStart");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="preStop"></a>
# **preStop**
> preStop()





### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AppLifecycleApi;


AppLifecycleApi apiInstance = new AppLifecycleApi();
try {
    apiInstance.preStop();
} catch (ApiException e) {
    System.err.println("Exception when calling AppLifecycleApi#preStop");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="readiness"></a>
# **readiness**
> readiness()





### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AppLifecycleApi;


AppLifecycleApi apiInstance = new AppLifecycleApi();
try {
    apiInstance.readiness();
} catch (ApiException e) {
    System.err.println("Exception when calling AppLifecycleApi#readiness");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

