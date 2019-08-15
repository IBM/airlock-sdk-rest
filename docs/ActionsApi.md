# ActionsApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**calculate**](ActionsApi.md#calculate) | **PUT** /products/{productInstanceId}/calculate | Executes all rules and generates an updated configuration for all features in the specified product based on the latest context.
[**getLastCalculate**](ActionsApi.md#getLastCalculate) | **GET** /products/{productInstanceId}/calculate | Returns the date of the last calculation performed in unix time format (milliseconds from epoch).
[**getLastPull**](ActionsApi.md#getLastPull) | **GET** /products/{productInstanceId}/pull | Returns the date of the last calculation performed in unix time format (milliseconds from epoch).
[**getLastPullLocale**](ActionsApi.md#getLastPullLocale) | **GET** /products/{productInstanceId}/locale | Returns locale of the last pull performed.
[**getLastSync**](ActionsApi.md#getLastSync) | **GET** /products/{productInstanceId}/sync | Returns the date of the last sync performed in unix time format (milliseconds from epoch).
[**pull**](ActionsApi.md#pull) | **PUT** /products/{productInstanceId}/pull | Downloads the latest rules and strings for a given product from the Airlock server. In case there is no server connectivity, Airlock will continue to use the existing rules and strings.
[**pullAllProducts**](ActionsApi.md#pullAllProducts) | **PUT** /products/pullAll | Downloads the latest rules and strings for all products from the Airlock server.In case there is no server connectivity, Airlock will continue to use the existing rules and strings.
[**sync**](ActionsApi.md#sync) | **PUT** /products/{productInstanceId}/sync | Exposes the latest calculation results.


<a name="calculate"></a>
# **calculate**
> String calculate(productInstanceId, sync)

Executes all rules and generates an updated configuration for all features in the specified product based on the latest context.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ActionsApi;


ActionsApi apiInstance = new ActionsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
Boolean sync = true; // Boolean | If true, the results will automatically replace the existing configuration. Otherwise, a sync call is required in order to expose the latest results (via /products/{productId}/sync).
try {
    String result = apiInstance.calculate(productInstanceId, sync);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ActionsApi#calculate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **sync** | **Boolean**| If true, the results will automatically replace the existing configuration. Otherwise, a sync call is required in order to expose the latest results (via /products/{productId}/sync). |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLastCalculate"></a>
# **getLastCalculate**
> Long getLastCalculate(productInstanceId)

Returns the date of the last calculation performed in unix time format (milliseconds from epoch).



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ActionsApi;


ActionsApi apiInstance = new ActionsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    Long result = apiInstance.getLastCalculate(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ActionsApi#getLastCalculate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

**Long**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLastPull"></a>
# **getLastPull**
> Long getLastPull(productInstanceId)

Returns the date of the last calculation performed in unix time format (milliseconds from epoch).



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ActionsApi;


ActionsApi apiInstance = new ActionsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    Long result = apiInstance.getLastPull(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ActionsApi#getLastPull");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

**Long**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLastPullLocale"></a>
# **getLastPullLocale**
> String getLastPullLocale(productInstanceId)

Returns locale of the last pull performed.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ActionsApi;


ActionsApi apiInstance = new ActionsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    String result = apiInstance.getLastPullLocale(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ActionsApi#getLastPullLocale");
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

<a name="getLastSync"></a>
# **getLastSync**
> Long getLastSync(productInstanceId)

Returns the date of the last sync performed in unix time format (milliseconds from epoch).



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ActionsApi;


ActionsApi apiInstance = new ActionsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    Long result = apiInstance.getLastSync(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ActionsApi#getLastSync");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

**Long**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="pull"></a>
# **pull**
> String pull(productInstanceId, locale)

Downloads the latest rules and strings for a given product from the Airlock server. In case there is no server connectivity, Airlock will continue to use the existing rules and strings.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ActionsApi;


ActionsApi apiInstance = new ActionsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
String locale = "\"en_US\""; // String | The current locale of the application. In case this locale differs from a previously supplied value, all of the cached data for this product will be cleared upon a successful pull.
try {
    String result = apiInstance.pull(productInstanceId, locale);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ActionsApi#pull");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **locale** | **String**| The current locale of the application. In case this locale differs from a previously supplied value, all of the cached data for this product will be cleared upon a successful pull. |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

<a name="pullAllProducts"></a>
# **pullAllProducts**
> String pullAllProducts()

Downloads the latest rules and strings for all products from the Airlock server.In case there is no server connectivity, Airlock will continue to use the existing rules and strings.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ActionsApi;


ActionsApi apiInstance = new ActionsApi();
try {
    String result = apiInstance.pullAllProducts();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ActionsApi#pullAllProducts");
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

<a name="sync"></a>
# **sync**
> String sync(productInstanceId)

Exposes the latest calculation results.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ActionsApi;


ActionsApi apiInstance = new ActionsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    String result = apiInstance.sync(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ActionsApi#sync");
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

