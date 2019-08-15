package com.ibm.airlock.rest.facades;

import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.rest.common.InstancesRetentionService;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.model.Feature;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeatureFacade {

    private static final Logger logger = Logger.getLogger(FeatureFacade.class.toString());

    public static Response getFeatureByName(String productInstanceId, String featureName) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            InstancesRetentionService.getInstance().setProductLastUsed(productInstanceId, System.currentTimeMillis());
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            Feature feature = new Feature(airlockProductManager.getFeature(featureName));
            return Response.status(200).entity(feature).build();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get feature failed").build();
        }
    }

    public static Response getFeatureChildren(String productInstanceId, String featureName) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            InstancesRetentionService.getInstance().setProductLastUsed(productInstanceId, System.currentTimeMillis());
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            com.ibm.airlock.common.data.Feature feature = airlockProductManager.getFeature(featureName);

            List<Feature> array = new ArrayList<>();
            for (com.ibm.airlock.common.data.Feature subFeature : feature.getChildren()) {
                array.add(new Feature(subFeature));
            }
            return Response.status(200).entity(array).build();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get feature children failed").build();
        }
    }

}
