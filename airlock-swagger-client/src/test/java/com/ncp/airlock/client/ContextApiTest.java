/*
 * 
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.ncp.airlock.client;

import com.ncp.airlock.client.invoker.ApiException;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ContextApi
 */
@Ignore
public class ContextApiTest {

    private final ContextApi api = new ContextApi();

    
    /**
     * Removes the specific product contextual information.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void clearContextTest() throws ApiException {
        String productInstanceId = null;
        String response = api.clearContext(productInstanceId);

        // TODO: test validations
    }
    
    /**
     * Removes the cross-product contextual information.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void clearSharedContextTest() throws ApiException {
        String response = api.clearSharedContext();

        // TODO: test validations
    }
    
    /**
     * Retrieves the current merged shared and product specific context.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getCurrentContextTest() throws ApiException {
        String productInstanceId = null;
        Map<String, Object> response = api.getCurrentContext(productInstanceId);

        // TODO: test validations
    }
    
    /**
     * Retrieves the merged shared and product specific context that was used in the last calculation operation.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getLastContextTest() throws ApiException {
        String productInstanceId = null;
        Map<String, Object> response = api.getLastContext(productInstanceId);

        // TODO: test validations
    }
    
    /**
     * Retrieves the current context of the specified product.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getProductContextTest() throws ApiException {
        String productInstanceId = null;
        Map<String, Object> response = api.getProductContext(productInstanceId);

        // TODO: test validations
    }
    
    /**
     * Retrieves the current cross-product contextual information.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getSharedContextTest() throws ApiException {
        Map<String, Object> response = api.getSharedContext();

        // TODO: test validations
    }
    
    /**
     * Updates the contextual information of the specified product. This context will be used in all following calculate operations until changed.  The specified context will be merged with the existing context, overriding any existing fields.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void updateProductContextTest() throws ApiException {
        String productInstanceId = null;
        Boolean clearPreviousContext = null;
        Map<String, Object> requestBody = null;
        String response = api.updateProductContext(productInstanceId, clearPreviousContext, requestBody);

        // TODO: test validations
    }
    
    /**
     * Updates the cross-product contextual information, shared by all products. This context will be used in all following calculate operations until changed. The specified context will be merged with the existing context, overriding any existing fields.
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void updateSharedContextTest() throws ApiException {
        Map<String, Object> requestBody = null;
        String response = api.updateSharedContext(requestBody);

        // TODO: test validations
    }
    
}
