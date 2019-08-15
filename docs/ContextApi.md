# ContextApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**clearContext**](ContextApi.md#clearContext) | **DELETE** /products/{productInstanceId}/context | Removes the specific product contextual information.
[**clearSharedContext**](ContextApi.md#clearSharedContext) | **DELETE** /products/context | Removes the cross-product contextual information.
[**getCurrentContext**](ContextApi.md#getCurrentContext) | **GET** /products/{productInstanceId}/context/current | Retrieves the current merged shared and product specific context.
[**getLastContext**](ContextApi.md#getLastContext) | **GET** /products/{productInstanceId}/context/last-calculated | Retrieves the merged shared and product specific context that was used in the last calculation operation.
[**getProductContext**](ContextApi.md#getProductContext) | **GET** /products/{productInstanceId}/context | Retrieves the current context of the specified product.
[**getSharedContext**](ContextApi.md#getSharedContext) | **GET** /products/context | Retrieves the current cross-product contextual information.
[**updateProductContext**](ContextApi.md#updateProductContext) | **PUT** /products/{productInstanceId}/context | Updates the contextual information of the specified product. This context will be used in all following calculate operations until changed.  The specified context will be merged with the existing context, overriding any existing fields.
[**updateSharedContext**](ContextApi.md#updateSharedContext) | **PUT** /products/context | Updates the cross-product contextual information, shared by all products. This context will be used in all following calculate operations until changed. The specified context will be merged with the existing context, overriding any existing fields.


<a name="clearContext"></a>
# **clearContext**
> String clearContext(productInstanceId)

Removes the specific product contextual information.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ContextApi;


ContextApi apiInstance = new ContextApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    String result = apiInstance.clearContext(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextApi#clearContext");
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

<a name="clearSharedContext"></a>
# **clearSharedContext**
> String clearSharedContext()

Removes the cross-product contextual information.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ContextApi;


ContextApi apiInstance = new ContextApi();
try {
    String result = apiInstance.clearSharedContext();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextApi#clearSharedContext");
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

<a name="getCurrentContext"></a>
# **getCurrentContext**
> java.util.Map&lt;String, Object&gt; getCurrentContext(productInstanceId)

Retrieves the current merged shared and product specific context.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ContextApi;


ContextApi apiInstance = new ContextApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    java.util.Map<String, Object> result = apiInstance.getCurrentContext(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextApi#getCurrentContext");
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

<a name="getLastContext"></a>
# **getLastContext**
> java.util.Map&lt;String, Object&gt; getLastContext(productInstanceId)

Retrieves the merged shared and product specific context that was used in the last calculation operation.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ContextApi;


ContextApi apiInstance = new ContextApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    java.util.Map<String, Object> result = apiInstance.getLastContext(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextApi#getLastContext");
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

<a name="getProductContext"></a>
# **getProductContext**
> java.util.Map&lt;String, Object&gt; getProductContext(productInstanceId)

Retrieves the current context of the specified product.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ContextApi;


ContextApi apiInstance = new ContextApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    java.util.Map<String, Object> result = apiInstance.getProductContext(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextApi#getProductContext");
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

<a name="getSharedContext"></a>
# **getSharedContext**
> java.util.Map&lt;String, Object&gt; getSharedContext()

Retrieves the current cross-product contextual information.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ContextApi;


ContextApi apiInstance = new ContextApi();
try {
    java.util.Map<String, Object> result = apiInstance.getSharedContext();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextApi#getSharedContext");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**java.util.Map&lt;String, Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateProductContext"></a>
# **updateProductContext**
> String updateProductContext(productInstanceId, clearPreviousContext, context)

Updates the contextual information of the specified product. This context will be used in all following calculate operations until changed.  The specified context will be merged with the existing context, overriding any existing fields.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ContextApi;


ContextApi apiInstance = new ContextApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
Boolean clearPreviousContext = true; // Boolean |  If true, the specified context will be used as is, disregarding any existing values. Otherwise, the specified context will be merged with the existing context, overriding any existing fields.
Object context = null; // Object | A JSON object containing all product specific contextual information that can be used in Airlock rules. Can contain nested values. Fields of this context will take precedence over any shared context fields of the same name.
try {
    String result = apiInstance.updateProductContext(productInstanceId, clearPreviousContext, context);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextApi#updateProductContext");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **clearPreviousContext** | **Boolean**|  If true, the specified context will be used as is, disregarding any existing values. Otherwise, the specified context will be merged with the existing context, overriding any existing fields. |
 **context** | **Object**| A JSON object containing all product specific contextual information that can be used in Airlock rules. Can contain nested values. Fields of this context will take precedence over any shared context fields of the same name. |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

<a name="updateSharedContext"></a>
# **updateSharedContext**
> String updateSharedContext(sharedContext)

Updates the cross-product contextual information, shared by all products. This context will be used in all following calculate operations until changed. The specified context will be merged with the existing context, overriding any existing fields.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ContextApi;


ContextApi apiInstance = new ContextApi();
Object sharedContext = null; // Object | A JSON object containing shared cross-product contextual information that can be used in Airlock rules. Can contain nested values. Product specific context fields (via /products/{productId}/context) will take precedence over shared context fields of the same name.
try {
    String result = apiInstance.updateSharedContext(sharedContext);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContextApi#updateSharedContext");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **sharedContext** | **Object**| A JSON object containing shared cross-product contextual information that can be used in Airlock rules. Can contain nested values. Product specific context fields (via /products/{productId}/context) will take precedence over shared context fields of the same name. |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

