# DebugApi

All URIs are relative to *http://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**clearCache**](DebugApi.md#clearCache) | **DELETE** /products/{productInstanceId}/cache | Clears the cached data for specific product
[**getExperiment**](DebugApi.md#getExperiment) | **GET** /products/{productInstanceId}/experiment | Retrieves current experiment info.
[**getExperiments**](DebugApi.md#getExperiments) | **GET** /products/{productInstanceId}/experiments | Retrieves the existing experiments info.
[**getFeatureStatuses**](DebugApi.md#getFeatureStatuses) | **GET** /products/{productInstanceId}/features | Retrieves all features of the specified product.
[**getProductBranches**](DebugApi.md#getProductBranches) | **GET** /products/{productInstanceId}/branches | Retrieves list of branch names existing on this product
[**getResponsiveMode**](DebugApi.md#getResponsiveMode) | **GET** /products/{productInstanceId}/responsive-mode | Gets product responsive mode streams for this product. DIRECT_MODE or CACHED_MODE
[**getSelectedBranch**](DebugApi.md#getSelectedBranch) | **GET** /products/{productInstanceId}/branch | Retrieves the current branch name is being used for this product.
[**getStreams**](DebugApi.md#getStreams) | **GET** /products/{productInstanceId}/streams | Gets all streams for this product.
[**isDeviceInFeaturePercentageRange**](DebugApi.md#isDeviceInFeaturePercentageRange) | **GET** /products/{productInstanceId}/percentage | Returns whether the device inside the feature percentage range or outside. The method is applicable for features, experiments,variant and streams
[**putProductBranch**](DebugApi.md#putProductBranch) | **PUT** /products/{productInstanceId}/branches/{branchName} | Sets the current branch to be used on this product.
[**runActionOnStream**](DebugApi.md#runActionOnStream) | **PUT** /products/{productInstanceId}/streams/{streamId}/actions/{actionId} | Apply stream action on specific stream, the available actions are: [reset], [clearTrace], [suspend]
[**setDevicePercentageRange4Feature**](DebugApi.md#setDevicePercentageRange4Feature) | **PUT** /products/{productInstanceId}/percentage | Sets the device to be inside the feature percentage range or outside. The method is applicable for features, experiments,variant and streams
[**setResponsiveMode**](DebugApi.md#setResponsiveMode) | **PUT** /products/{productInstanceId}/responsive-mode | Update product responsive mode streams for this product. DIRECT_MODE or CACHED_MODE
[**statistic**](DebugApi.md#statistic) | **GET** /products/statistic | Retrieves the server statistic data.


<a name="clearCache"></a>
# **clearCache**
> String clearCache(productInstanceId)

Clears the cached data for specific product

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    String result = apiInstance.clearCache(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#clearCache");
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
 - **Accept**: text/plain

<a name="getExperiment"></a>
# **getExperiment**
> Experiment getExperiment(productInstanceId)

Retrieves current experiment info.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    Experiment result = apiInstance.getExperiment(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#getExperiment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

[**Experiment**](Experiment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getExperiments"></a>
# **getExperiments**
> ExperimentList getExperiments(productInstanceId)

Retrieves the existing experiments info.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    ExperimentList result = apiInstance.getExperiments(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#getExperiments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

[**ExperimentList**](ExperimentList.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFeatureStatuses"></a>
# **getFeatureStatuses**
> List&lt;Feature&gt; getFeatureStatuses(productInstanceId)

Retrieves all features of the specified product.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    List<Feature> result = apiInstance.getFeatureStatuses(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#getFeatureStatuses");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

[**List&lt;Feature&gt;**](Feature.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProductBranches"></a>
# **getProductBranches**
> List&lt;List&lt;Object&gt;&gt; getProductBranches(productInstanceId)

Retrieves list of branch names existing on this product

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    List<List<Object>> result = apiInstance.getProductBranches(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#getProductBranches");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

[**List&lt;List&lt;Object&gt;&gt;**](List.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getResponsiveMode"></a>
# **getResponsiveMode**
> String getResponsiveMode(productInstanceId)

Gets product responsive mode streams for this product. DIRECT_MODE or CACHED_MODE

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    String result = apiInstance.getResponsiveMode(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#getResponsiveMode");
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
 - **Accept**: text/plain

<a name="getSelectedBranch"></a>
# **getSelectedBranch**
> String getSelectedBranch(productInstanceId)

Retrieves the current branch name is being used for this product.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    String result = apiInstance.getSelectedBranch(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#getSelectedBranch");
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
 - **Accept**: text/plain

<a name="getStreams"></a>
# **getStreams**
> Stream getStreams(productInstanceId)

Gets all streams for this product.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    Stream result = apiInstance.getStreams(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#getStreams");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

[**Stream**](Stream.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="isDeviceInFeaturePercentageRange"></a>
# **isDeviceInFeaturePercentageRange**
> String isDeviceInFeaturePercentageRange(productInstanceId, section, itemName)

Returns whether the device inside the feature percentage range or outside. The method is applicable for features, experiments,variant and streams

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String section = "section_example"; // String | The section of the item updated: could be features/experiments/streams
String itemName = "itemName_example"; // String | The name of the item updated
try {
    String result = apiInstance.isDeviceInFeaturePercentageRange(productInstanceId, section, itemName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#isDeviceInFeaturePercentageRange");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **section** | **String**| The section of the item updated: could be features/experiments/streams |
 **itemName** | **String**| The name of the item updated |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

<a name="putProductBranch"></a>
# **putProductBranch**
> String putProductBranch(productInstanceId, branchName)

Sets the current branch to be used on this product.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String branchName = "branchName_example"; // String | 
try {
    String result = apiInstance.putProductBranch(productInstanceId, branchName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#putProductBranch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **branchName** | **String**|  |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

<a name="runActionOnStream"></a>
# **runActionOnStream**
> String runActionOnStream(productInstanceId, streamId, actionId, doSuspend)

Apply stream action on specific stream, the available actions are: [reset], [clearTrace], [suspend]

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String streamId = "streamId_example"; // String | 
String actionId = "actionId_example"; // String | 
Boolean doSuspend = true; // Boolean | 
try {
    String result = apiInstance.runActionOnStream(productInstanceId, streamId, actionId, doSuspend);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#runActionOnStream");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **streamId** | **String**|  |
 **actionId** | **String**|  |
 **doSuspend** | **Boolean**|  | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="setDevicePercentageRange4Feature"></a>
# **setDevicePercentageRange4Feature**
> String setDevicePercentageRange4Feature(productInstanceId, section, itemName, body)

Sets the device to be inside the feature percentage range or outside. The method is applicable for features, experiments,variant and streams

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String section = "section_example"; // String | The section of the item updated: could be features/experiments/streams
String itemName = "itemName_example"; // String | The name of the item updated
String body = "body_example"; // String | Specifies whether the device in the feature percentage range or out.
try {
    String result = apiInstance.setDevicePercentageRange4Feature(productInstanceId, section, itemName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#setDevicePercentageRange4Feature");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **section** | **String**| The section of the item updated: could be features/experiments/streams |
 **itemName** | **String**| The name of the item updated |
 **body** | **String**| Specifies whether the device in the feature percentage range or out. | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: text/plain
 - **Accept**: text/plain

<a name="setResponsiveMode"></a>
# **setResponsiveMode**
> String setResponsiveMode(productInstanceId, body)

Update product responsive mode streams for this product. DIRECT_MODE or CACHED_MODE

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
String body = "body_example"; // String | Specifies whether the device in the feature percentage range or out.
try {
    String result = apiInstance.setResponsiveMode(productInstanceId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#setResponsiveMode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **body** | **String**| Specifies whether the device in the feature percentage range or out. | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

<a name="statistic"></a>
# **statistic**
> String statistic()

Retrieves the server statistic data.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.DebugApi;


DebugApi apiInstance = new DebugApi();
try {
    String result = apiInstance.statistic();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DebugApi#statistic");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

