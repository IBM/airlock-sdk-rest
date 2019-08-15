# UserGroupsApi

All URIs are relative to *https://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllUserGroups**](UserGroupsApi.md#getAllUserGroups) | **GET** /products/{productInstanceId}/usergroups/all | Retrieves all user groups exists for the specified product on the server.
[**getUserGroups**](UserGroupsApi.md#getUserGroups) | **GET** /products/{productInstanceId}/usergroups | Retrieves all user groups that were set for the specified product.
[**updateUserGroupsToProduct**](UserGroupsApi.md#updateUserGroupsToProduct) | **PUT** /products/{productInstanceId}/usergroups | Updates the list of user groups for the specified product with the specified list. All previously set user groups will be overridden with this list, unless the product has the same user groups list.


<a name="getAllUserGroups"></a>
# **getAllUserGroups**
> java.util.List&lt;Object&gt; getAllUserGroups(productInstanceId)

Retrieves all user groups exists for the specified product on the server.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserGroupsApi;


UserGroupsApi apiInstance = new UserGroupsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    java.util.List<Object> result = apiInstance.getAllUserGroups(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserGroupsApi#getAllUserGroups");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

**java.util.List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUserGroups"></a>
# **getUserGroups**
> java.util.List&lt;Object&gt; getUserGroups(productInstanceId)

Retrieves all user groups that were set for the specified product.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserGroupsApi;


UserGroupsApi apiInstance = new UserGroupsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    java.util.List<Object> result = apiInstance.getUserGroups(productInstanceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserGroupsApi#getUserGroups");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |

### Return type

**java.util.List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateUserGroupsToProduct"></a>
# **updateUserGroupsToProduct**
> updateUserGroupsToProduct(productInstanceId, body)

Updates the list of user groups for the specified product with the specified list. All previously set user groups will be overridden with this list, unless the product has the same user groups list.



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.UserGroupsApi;


UserGroupsApi apiInstance = new UserGroupsApi();
String productInstanceId = "\"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\""; // String | The product id as returned by the init function or taken directly from the defaults file.
java.util.List<String> body = Arrays.asList(new java.util.List<String>()); // java.util.List<String> | An array of strings containing the user groups to set.
try {
    apiInstance.updateUserGroupsToProduct(productInstanceId, body);
} catch (ApiException e) {
    System.err.println("Exception when calling UserGroupsApi#updateUserGroupsToProduct");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **body** | **java.util.List&lt;String&gt;**| An array of strings containing the user groups to set. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

