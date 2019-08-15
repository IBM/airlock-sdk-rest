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
 * API tests for NotificationApi
 */
@Ignore
public class NotificationApiTest {

    private final NotificationApi api = new NotificationApi();

    
    /**
     * Update notification from VAS into Airlock shared context
     *
     * @param ERROR_UNKNOWN  (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    @Test
    public void notifyTest() throws ApiException {
        List<Map<String, Object>> ERROR_UNKNOWN = null;
        api.mergeVASNotificationIntoContext(ERROR_UNKNOWN);
    }
}
