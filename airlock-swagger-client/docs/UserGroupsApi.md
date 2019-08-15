# UserGroupsApi

All URIs are relative to *http://localhost/airlock/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllUserGroups**](UserGroupsApi.md#getAllUserGroups) | **GET** /products/{productInstanceId}/usergroups/all | Retrieves all user groups exists for the specified product on the server.
[**getUserGroups**](UserGroupsApi.md#getUserGroups) | **GET** /products/{productInstanceId}/usergroups | Retrieves all user groups that were set for the specified product.
[**updateUserGroupsToProduct**](UserGroupsApi.md#updateUserGroupsToProduct) | **PUT** /products/{productInstanceId}/usergroups | Updates the list of user groups for the specified product with the specified list. All previously set user groups will be overridden with this list, unless the product has the same user groups list.


<a name="getAllUserGroups"></a>
# **getAllUserGroups**
> List&lt;Object&gt; getAllUserGroups(productInstanceId)

Retrieves all user groups exists for the specified product on the server.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.UserGroupsApi;


UserGroupsApi apiInstance = new UserGroupsApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    List<Object> result = apiInstance.getAllUserGroups(productInstanceId);
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

**List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUserGroups"></a>
# **getUserGroups**
> List&lt;Object&gt; getUserGroups(productInstanceId)

Retrieves all user groups that were set for the specified product.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.UserGroupsApi;


UserGroupsApi apiInstance = new UserGroupsApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
try {
    List<Object> result = apiInstance.getUserGroups(productInstanceId);
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

**List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateUserGroupsToProduct"></a>
# **updateUserGroupsToProduct**
> String updateUserGroupsToProduct(productInstanceId, ERROR_UNKNOWN)

Updates the list of user groups for the specified product with the specified list. All previously set user groups will be overridden with this list, unless the product has the same user groups list.

### Example
```java
// Import classes:
//import com.ncp.airlock.client.invoker.ApiException;
//import com.ncp.airlock.client.UserGroupsApi;


UserGroupsApi apiInstance = new UserGroupsApi();
String productInstanceId = "productInstanceId_example"; // String | The product id as returned by the init function or taken directly from the defaults file.
List<String> ERROR_UNKNOWN = Arrays.asList(new List()); // List<String> | An array of strings containing the user groups to set.
try {
    String result = apiInstance.updateUserGroupsToProduct(productInstanceId, ERROR_UNKNOWN);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserGroupsApi#updateUserGroupsToProduct");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **productInstanceId** | **String**| The product id as returned by the init function or taken directly from the defaults file. |
 **ERROR_UNKNOWN** | [**List&lt;String&gt;**](List.md)| An array of strings containing the user groups to set. | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain

