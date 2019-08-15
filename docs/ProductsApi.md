# ProductsApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**delete**](ProductsApi.md#delete) | **DELETE** /products/{productInstanceId} | Removes the specified product completely from the system including all cached data related to it. Parallel to uninstalling the application.
[**getAllProducts**](ProductsApi.md#getAllProducts) | **GET** /products | Retrieves all products.
[**getProductByID**](ProductsApi.md#getProductByID) | **GET** /products/{productInstanceId} | Retrieves a product by its id.
[**init**](ProductsApi.md#init) | **POST** /products/init | Initializes a product in Airlock. Must be called once before any other Airlock function can be used in this product. In case the product is already initialized and the product&#39;s version or defaults file does not match the previously initialized product then all of the product&#39;s cached data will be cleared except for user groups. Otherwise, this operation will have no affect.


<a name="delete"></a>
# **delete**
> String delete(productInstanceId)

Removes the specified product completely from the system including all cached data related to it. Parallel to uninstalling the application.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    String result = apiInstance.delete(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#delete");
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

<a name="getAllProducts"></a>
# **getAllProducts**
> java.util.List&lt;Product&gt; getAllProducts()

Retrieves all products.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
try {
    java.util.List<Product> result = apiInstance.getAllProducts();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#getAllProducts");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**java.util.List&lt;Product&gt;**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProductByID"></a>
# **getProductByID**
> Product getProductByID(productInstanceId)

Retrieves a product by its id.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    Product result = apiInstance.getProductByID(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#getProductByID");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

[**Product**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="init"></a>
# **init**
> Product init(appVersion, productDefaults, encryptionKey)

Initializes a product in Airlock. Must be called once before any other Airlock function can be used in this product. In case the product is already initialized and the product&#39;s version or defaults file does not match the previously initialized product then all of the product&#39;s cached data will be cleared except for user groups. Otherwise, this operation will have no affect.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProductsApi;


ProductsApi apiInstance = new ProductsApi();
String appVersion = "\"1.0.0\""; // String | The current version of the application requesting the initialization. The version range of the provided defaults file must include this version.
Object productDefaults = null; // Object | The contents of the defaults file downloaded from the Airlock Control Center.
String encryptionKey = "encryptionKey_example"; // String | The product encryption key. The key is intended to protect a product cached data, is specific for each product and could be downloaded from the Airlock Control Center.
try {
    Product result = apiInstance.init(appVersion, productDefaults, encryptionKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProductsApi#init");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appVersion** | **String**| The current version of the application requesting the initialization. The version range of the provided defaults file must include this version. |
 **productDefaults** | **Object**| The contents of the defaults file downloaded from the Airlock Control Center. |
 **encryptionKey** | **String**| The product encryption key. The key is intended to protect a product cached data, is specific for each product and could be downloaded from the Airlock Control Center. | [optional]

### Return type

[**Product**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

