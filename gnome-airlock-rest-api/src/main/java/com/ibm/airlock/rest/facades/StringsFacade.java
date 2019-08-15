package com.ibm.airlock.rest.facades;

import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StringsFacade {

    private static final Logger logger = Logger.getLogger(StringsFacade.class.toString());

    public static Response getStringByKey(String productInstanceId, String key,
                                          String[] arguments) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            if (airlockProductManager.getString(key) == null || airlockProductManager.getString(key).isEmpty()) {
                return Response.status(404).entity("Key not found").build();
            }

            return Response.status(200).entity(airlockProductManager.getString(key, arguments)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get string value by key failed").build();
        }
    }

    public static Response getAllStrings(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            return Response.status(200).entity(airlockProductManager.getAllStrings()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get all strings failed").build();
        }
    }
}
