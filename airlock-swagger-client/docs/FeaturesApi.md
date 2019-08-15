# FeaturesApi

All URIs are relative to *http://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getFeatureByName**](FeaturesApi.md#getFeatureByName) | **GET** /products/{productInstanceId}/features/{featureName} | Retrieves the feature with the specified name.
[**getFeatureChildren**](FeaturesApi.md#getFeatureChildren) | **GET** /products/{productInstanceId}/features/{featureName}/children | Retrieves the children of the specified feature.


<a name="getFeatureByName"></a>
# **getFeatureByName**
> Feature getFeatureByName(productInstanceId, featureName)

Retrieves the feature with the specified name.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.FeaturesApi;


FeaturesApi apiInstance = new FeaturesApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String featureName = "featureName_example"; // String | The name of the feature to retrieve.
try {
    Feature result = apiInstance.getFeatureByName(productInstanceId, featureName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeaturesApi#getFeatureByName");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **featureName** | **String**| The name of the feature to retrieve. |

### Return type

[**Feature**](Feature.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFeatureChildren"></a>
# **getFeatureChildren**
> List&lt;Feature&gt; getFeatureChildren(productInstanceId, featureName)

Retrieves the children of the specified feature.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.FeaturesApi;


FeaturesApi apiInstance = new FeaturesApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String featureName = "featureName_example"; // String | The name of the feature to retrieve the children for.
try {
    List<Feature> result = apiInstance.getFeatureChildren(productInstanceId, featureName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FeaturesApi#getFeatureChildren");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **featureName** | **String**| The name of the feature to retrieve the children for. |

### Return type

[**List&lt;Feature&gt;**](Feature.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

