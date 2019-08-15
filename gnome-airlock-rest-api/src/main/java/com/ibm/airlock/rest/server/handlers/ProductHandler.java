package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.ProductFacade;
import com.ibm.airlock.sdk.cache.pref.FilePreferencesFactory;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Denis Voloshin
 */

public class ProductHandler extends BaseHandler {

    private static final String KEY = "encryptionKey";
    private static final String APP_VERSION = "appVersion";

    @Override
    public void post(HttpExchange request) {

        String instanceId = UUID.randomUUID().toString();
        String appVersion = queryParameters.get(APP_VERSION);
        String encryptionKey = queryParameters.get(KEY);

        Response response = ProductFacade.init(FilePreferencesFactory.getAirlockCacheDirectory(),
                body, encryptionKey, appVersion, instanceId);

        sendRespone(response, request, Response.ContentType.APPLICATION_JSON);
    }

    @Override
    public void get(HttpExchange request) {

        if (!getPathParameter(4).isPresent()) {
            sendRespone(ProductFacade.getAll(), request, Response.ContentType.APPLICATION_JSON);
        }

        getPathParameter(4).ifPresent(instanceId -> {
            sendRespone(ProductFacade.get(instanceId), request, Response.ContentType.APPLICATION_JSON);
        });
    }

    @Override
    public void delete(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            sendRespone(ProductFacade.delete(instanceId), request, Response.ContentType.TEXT_PLAIN);
        });
    }


    public static String getProductId(String defaultFile) throws JSONException {
        if (defaultFile == null) {
            return null;
        } else {
            JSONObject defaultFileJson = new JSONObject(defaultFile);
            return defaultFileJson.optString("productId");
        }
    }
}
