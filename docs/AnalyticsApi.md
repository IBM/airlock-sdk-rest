# AnalyticsApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllFieldsForAnalytics**](AnalyticsApi.md#getAllFieldsForAnalytics) | **GET** /products/{productInstanceId}/analytics | Retrieves all analytics data marked to be sent in the Control Center including context fields and feature information.
[**getFeatureAttributesForAnalytics**](AnalyticsApi.md#getFeatureAttributesForAnalytics) | **GET** /products/{productInstanceId}/analytics/features/{featureName} | Retrieves all analytics data marked to be sent in the Control Center for the specified feature.


<a name="getAllFieldsForAnalytics"></a>
# **getAllFieldsForAnalytics**
> AnalyticsData getAllFieldsForAnalytics(productInstanceId)

Retrieves all analytics data marked to be sent in the Control Center including context fields and feature information.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AnalyticsApi;


AnalyticsApi apiInstance = new AnalyticsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    AnalyticsData result = apiInstance.getAllFieldsForAnalytics(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnalyticsApi#getAllFieldsForAnalytics");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

[**AnalyticsData**](AnalyticsData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFeatureAttributesForAnalytics"></a>
# **getFeatureAttributesForAnalytics**
> FeatureAnalytics getFeatureAttributesForAnalytics(productInstanceId, featureName)

Retrieves all analytics data marked to be sent in the Control Center for the specified feature.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AnalyticsApi;


AnalyticsApi apiInstance = new AnalyticsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
String featureName = "featureName_example"; // String | The name of the feature to retrieve analytics data for.
try {
    FeatureAnalytics result = apiInstance.getFeatureAttributesForAnalytics(productInstanceId, featureName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnalyticsApi#getFeatureAttributesForAnalytics");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **featureName** | **String**| The name of the feature to retrieve analytics data for. |

### Return type

[**FeatureAnalytics**](FeatureAnalytics.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

