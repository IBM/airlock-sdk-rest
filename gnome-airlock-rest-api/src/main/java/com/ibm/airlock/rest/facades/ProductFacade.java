package com.ibm.airlock.rest.facades;

import com.ibm.airlock.common.AirlockInvalidFileException;
import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.rest.common.InstancesRetentionService;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.model.Product;
import com.ibm.airlock.rest.util.Strings;
import com.ibm.airlock.sdk.AbstractMultiProductManager;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.airlock.sdk.AirlockProductInstanceManager;
import com.ibm.airlock.sdk.cache.InstanceContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductFacade {

    private static final Logger logger = Logger.getLogger(ProductFacade.class.toString());

    public static AbstractMultiProductManager.ProductMetaData getProductById(String instanceId) {
        return AirlockMultiProductsManager.getInstance().getAllProducts().stream().
                filter((s) -> s.getInstanceId().equals(instanceId)).findFirst().get();
    }

    public static Response init(String productCacheFolder, String productDefaults, String encryptionKey, String appVersion, String instanceId) {

        String productId = getProductId(productDefaults);
        try {
            if (productId == null || productId.isEmpty()) {
                return Response.status(200).entity("Bad defaults file format, product id not found").build();
            }
            InstanceContext instanceContext = new InstanceContext(instanceId, productCacheFolder, productDefaults, appVersion);
            AirlockProductInstanceManager airlockProductInstanceManager = AirlockMultiProductsManager.getInstance().createProduct(instanceContext);
            airlockProductInstanceManager.initSDK(instanceContext, productDefaults, appVersion, encryptionKey);
            InstancesRetentionService.getInstance().setProductLastUsed(instanceId, System.currentTimeMillis());
            logger.fine("Init completed successfully");
            return Response.status(200).entity(new Product(getProductById(instanceId))).build();
        } catch (JSONException | AirlockInvalidFileException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(400).entity("Init failed: " + e.getMessage()).build();
        } catch (Exception e) {
            // remove
            AirlockMultiProductsManager.getInstance().removeAirlockProductManager(instanceId);
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Init failed: " + e.getMessage()).build();
        }
    }

    public static String getProductId(String defaultFile) throws JSONException {
        if (defaultFile == null) {
            return null;
        } else {
            JSONObject defaultFileJson = new JSONObject(defaultFile);
            return defaultFileJson.optString("productId");
        }
    }

    public static Response delete(String productInstanceId) {
        try {
            //Check if already destroyed
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                logger.warning("Product [" + productInstanceId + "] doesn't exist");
                return Response.status(404).build();
            }
            AirlockMultiProductsManager.getInstance().removeAirlockProductManager(productInstanceId);
        } catch (Exception e) {
            logger.severe(e.getMessage()+"\n"+ Strings.stackTraceToString(e));
            return Response.status(500).entity("Delete product failed").build();
        }
        logger.fine("Product [" + productInstanceId + "] was deleted");
        return Response.status(200).entity("Product was deleted").build();
    }

    public static Response get(String productInstanceId) {
        Product product = null;
        try {
            Collection<AbstractMultiProductManager.ProductMetaData> products = AirlockMultiProductsManager.getInstance().getAllProducts();
            for (AirlockMultiProductsManager.ProductMetaData productMetaData : products) {
                if (productMetaData.getInstanceId().equals(productInstanceId)) {
                    product = new Product(productMetaData);
                }
            }
            //Check not already init
            if (product == null) {
                return Response.status(404).entity("Product not found").build();
            } else {
                return Response.status(200).entity(product).build();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get product by id failed").build();
        }
    }


    public static Response getAll() {
        try {
            Collection<AirlockMultiProductsManager.ProductMetaData> products = AirlockMultiProductsManager.getInstance().getAllProducts();
            if (products == null) {
                return Response.status(404).entity("No products found").build();
            }
            List<Product> response = new ArrayList<>();
            for (AirlockMultiProductsManager.ProductMetaData product : products) {
                response.add(new Product(product));
            }
            return Response.status(200).entity(response).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get all products failed").build();
        }
    }
}
