package com.ibm.airlock.rest.facades;

import com.google.gson.Gson;
import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.common.engine.StateFullContext;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import io.swagger.annotations.*;
import org.json.JSONObject;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContextFacade {

    private static final Logger logger = Logger.getLogger(ContextFacade.class.toString());

    public static Response updateSharedContext(String sharedContext) {
        try {
            AirlockMultiProductsManager.getInstance().updateSharedContext(sharedContext, false);
            return Response.status(200).entity("Shared context was updated successfully").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Set shared context failed").build();
        }
    }


    public static Response clearSharedContext() {
        try {
            AirlockMultiProductsManager.getInstance().updateSharedContext("{}", true);
            return Response.status(200).entity("Shared context was updated successfully").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Clear shared context failed").build();
        }
    }

    public static Response getSharedContext() {
        try {
            return Response.status(200).entity(
                    new Gson().fromJson(AirlockMultiProductsManager.getInstance().getMultiProductsSharedScope().toString(), Map.class))
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get shared context failed").build();
        }
    }

    public static Response updateProductContext(String productInstanceId, String context, Boolean clearPreviousContext) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            airlockProductManager.updateProductContext(context, clearPreviousContext);
            return Response.status(200).entity("Product context was updated successfully").build();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Change product context failed").build();
        }
    }

    public static Response clearContext(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            airlockProductManager.updateProductContext("{}", true);
            return Response.status(200).entity("Product context was cleared successfully").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Clear product context failed").build();
        }
    }

    public static Response getProductContext(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            return Response.status(200).entity(new Gson().fromJson(
                    airlockProductManager.getAirlockContextManager().getCurrentContext().toString(), Map.class)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get product context failed").build();
        }
    }

    public static Response getLastContext(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            return Response.status(200).entity(new Gson().fromJson(
                    airlockProductManager.getAirlockContextManager().getRuntimeContext().toString(), Map.class)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get product context failed").build();
        }
    }

    public  static Response getCurrentContext(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            StateFullContext shareContext = AirlockMultiProductsManager.getInstance().getMultiProductsSharedScope();
            StateFullContext productContext = airlockProductManager.getAirlockContextManager().getCurrentContext();
            productContext.mergeWith(shareContext);
            return Response.status(200).entity(new Gson().fromJson(productContext.toString(), Map.class)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get product context failed").build();
        }
    }

}
