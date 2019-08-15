package com.ibm.airlock.rest.facades;

import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.rest.common.InstancesRetentionService;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.model.Feature;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.airlock.sdk.debug.AirlockDebugger;
import com.ibm.airlock.sdk.util.ProductLocaleProvider;
import org.json.JSONObject;

import java.lang.reflect.MalformedParametersException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductActionsFacade {
    private static final Logger logger = Logger.getLogger(ProductActionsFacade.class.toString());

    public static Response pull(String productInstanceId, String locale) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            InstancesRetentionService.getInstance().setProductLastUsed(productInstanceId, System.currentTimeMillis());
            try {
                if (locale == null) {
                    return Response.status(412).entity("Set locale failed, locale not provided").build();
                }
                ProductLocaleProvider productLocaleProvider = new ProductLocaleProvider(locale);
                if (!productLocaleProvider.getLocale().equals(airlockProductManager.getLocale())) {
                    airlockProductManager.setLocaleProvider(productLocaleProvider);
                    airlockProductManager.resetLocale();
                }
            } catch (MalformedParametersException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                return Response.status(412).entity("Set locale failed").build();
            }

            AirlockDebugger airlockDebugProductManager = AirlockMultiProductsManager.getInstance().getAirlockDebugger(productInstanceId);
            airlockDebugProductManager.pullFeatures();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Pull failed, error " + e.getMessage()).build();
        }
        logger.fine("Pull Succeeded");
        return Response.status(200).entity("Pull succeeded").build();
    }

    public static Response getLastPullTime(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);

            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            Date lastPullTime = airlockProductManager.getLastPullTime();
            logger.fine("last pull called" + lastPullTime);

            return Response.status(200).entity(lastPullTime.getTime()+"").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get last pull time failed").build();
        }
    }

    public static Response pullAll() {
        try {
            Collection<AirlockMultiProductsManager.ProductMetaData> products = AirlockMultiProductsManager.getInstance().getAllProducts();
            for (AirlockMultiProductsManager.ProductMetaData product : products) {
                AirlockDebugger airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockDebugger(product.getInstanceId());
                InstancesRetentionService.getInstance().setProductLastUsed(product.getProductId(), System.currentTimeMillis());
                if (airlockProductManager != null) {
                    airlockProductManager.pullFeatures();
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Pull failed").build();
        }
        logger.fine("Pull Succeeded");
        return Response.status(200).entity("Pull succeeded").build();
    }

    public static Response calculate(String productInstanceId, boolean isSync) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.
                    getInstance().getAirlockProductManager(productInstanceId);
            InstancesRetentionService.getInstance().setProductLastUsed(productInstanceId, System.currentTimeMillis());
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            airlockProductManager.calculateFeatures((JSONObject) null, (JSONObject) null);

            logger.fine("Calculate Succeeded");
            if (isSync) {
                airlockProductManager.syncFeatures();
                logger.fine("Sync after Calculate Succeeded");
            }

            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Calculate failed").build();
        }
    }

    public static Response getLastCalculateTime(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            Date lastCalculateTime = airlockProductManager.getLastCalculateTime();
            return Response.status(200).entity(lastCalculateTime.getTime()+"").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get last calculate time failed").build();
        }
    }

    public static Response sync(String productInstanceId) {
        AirlockProductManager airlockProductManager = null;
        try {
            airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            InstancesRetentionService.getInstance().setProductLastUsed(productInstanceId, System.currentTimeMillis());
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            airlockProductManager.syncFeatures();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Synchronize failed").build();
        }
        logger.fine("Synchronize Succeeded");

        return Response.status(200).entity("").build();
    }

    public static Response getLastSyncTime(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            Date lastSyncTime = airlockProductManager.getLastSyncTime();
            return Response.status(200).entity(lastSyncTime.getTime()+"").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get last sync time failed").build();
        }
    }

    private static List<Feature> getSyncedFeatures(AirlockProductManager airlockProductManager) {
        List<com.ibm.airlock.common.data.Feature> rootFeatures = airlockProductManager.getSyncFeatureList().getRootFeatures();
        if (rootFeatures == null) {
            return new ArrayList<>();
        }
        List<Feature> outRootFeatures = new ArrayList<>();
        for (com.ibm.airlock.common.data.Feature singleFeature : rootFeatures) {
            outRootFeatures.add(new Feature(singleFeature));
        }
        return outRootFeatures;
    }

    public static Response getLastPullLocale(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            Locale locale = airlockProductManager.getLocale();
            return Response.status(200).entity(locale.toString()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get last sync time failed").build();
        }
    }
}
