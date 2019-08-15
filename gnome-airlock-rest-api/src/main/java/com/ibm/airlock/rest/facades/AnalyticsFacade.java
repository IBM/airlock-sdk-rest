package com.ibm.airlock.rest.facades;

import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.common.data.Feature;
import com.ibm.airlock.common.util.Constants;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.model.AnalyticsData;
import com.ibm.airlock.rest.model.Attribute;
import com.ibm.airlock.rest.model.FeatureAnalytics;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyticsFacade {
    private static final Logger logger = Logger.getLogger(AnalyticsFacade.class.toString());


    public static Response getAllFieldsForAnalytics(String productInstanceId) {
        try {

            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);

            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            Map<String, String> experimentInfo = airlockProductManager.getExperimentInfo();

            String variantName = experimentInfo.get(Constants.JSON_FIELD_VARIANT);

            String experimentName = experimentInfo.get(Constants.JSON_FIELD_EXPERIMENT);

            String dateJoinedString = experimentInfo.get(Constants.JSON_FIELD_VARIANT_DATE_JOINED);
            Long dateJoined = null;
            if (dateJoinedString != null && !dateJoinedString.isEmpty()) {
                dateJoined = Long.valueOf(experimentInfo.get(Constants.JSON_FIELD_VARIANT_DATE_JOINED));
            }

            String branchName = airlockProductManager.getLastBranchName();

            AnalyticsData analyticsData = new AnalyticsData(experimentName, branchName, variantName, dateJoined);

            analyticsData.setFeatures(getFeaturesAnalyticsData(airlockProductManager));

            analyticsData.setContext(getContextData(airlockProductManager));

            return Response.status(200).entity(analyticsData).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get analytics data failed").build();
        }
    }

    public static Response getFeatureAttributesForAnalytics(String productInstanceId, String featureName) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            Feature feature = airlockProductManager.getFeature(featureName);
            if (feature.getSource() == Feature.Source.MISSING) {
                return Response.status(404).entity("Feature not found").build();
            }
            FeatureAnalytics analytics = getSingleFeaturesAnalyticsData(feature);
            return Response.status(200).entity(analytics == null ? "" : analytics).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get feature analytics failed").build();
        }
    }

    private static Attribute[] getContextData(AirlockProductManager airlockProductManager) {
        List<Attribute> contextAttributes = new ArrayList<>();

        JSONObject context = new JSONObject(airlockProductManager.getAirlockContextManager().getRuntimeContext().toString());

        JSONObject contextFieldValues = airlockProductManager.getContextFieldsValuesForAnalytics(context);
        for (Object key : contextFieldValues.keySet()) {
            //based on you key types
            String keyStr = (String) key;
            String outputKey;
            if (keyStr.startsWith("context.")) {
                outputKey = keyStr.substring(8);
            } else {
                outputKey = keyStr;
            }
            Object value = contextFieldValues.get(keyStr);
            contextAttributes.add(new Attribute(outputKey, value));
        }
        return contextAttributes.toArray(new Attribute[contextAttributes.size()]);

    }

    private static FeatureAnalytics[] getFeaturesAnalyticsData(AirlockProductManager airlockProductManager) {
        List<FeatureAnalytics> features = new ArrayList<>();

        List<Feature> rootFeatures = airlockProductManager.getRootFeatures();

        if (rootFeatures.isEmpty()) {
            return null;
        }

        for (Feature rootFeature : rootFeatures) {
            List<Feature> innerFeatures = getAllChildFeatures(rootFeature);
            int innerFeaturesListSize = innerFeatures.size();
            for (int i = 0; i < innerFeaturesListSize; i++) {
                Feature innerFeature = innerFeatures.get(i);
                FeatureAnalytics featureDetails = getSingleFeaturesAnalyticsData(innerFeature);
                if (featureDetails != null) {
                    features.add(featureDetails);
                }
            }
        }
        return features.toArray(new FeatureAnalytics[features.size()]);
    }

    private  static FeatureAnalytics getSingleFeaturesAnalyticsData(Feature feature) {
        boolean shouldBeReported = false;
        FeatureAnalytics featureDetails = new FeatureAnalytics(feature.getName());
        boolean isEnabledForAnalytics = feature.isEnabledForAnalytics();
        featureDetails.setSendOn(isEnabledForAnalytics);
        if (isEnabledForAnalytics) {
            shouldBeReported = true;
        }
        List<String> appliedRules = feature.getAnalyticsAppliedRules();
        if (appliedRules != null && appliedRules.size() > 0) {
            featureDetails.setAppliedConfigurationRules(appliedRules.toArray(new String[appliedRules.size()]));
            shouldBeReported = true;
        }

        JSONArray appliedOrderingRules = feature.getAnalyticsAppliedOrderRules();
        if (appliedOrderingRules != null && appliedOrderingRules.length() > 0) {
            List<String> orderingRules = new ArrayList<>();
            for (int j = 0; j < appliedOrderingRules.length(); j++) {
                shouldBeReported = true;
                orderingRules.add(appliedOrderingRules.getString(j));
            }
            featureDetails.setAppliedOrderingRules(orderingRules.toArray(new String[orderingRules.size()]));//TODO - verify !!!
        }

        JSONArray orderedChildren = feature.getAnalyticsOrderedFeatures();
        if (orderedChildren != null && orderedChildren.length() > 0) {
            List<String> orderedChildrenList = new ArrayList<>();
            for (int j = 0; j < orderedChildren.length(); j++) {
                shouldBeReported = true;
                orderedChildrenList.add(orderedChildren.getString(j));
            }
            featureDetails.setOrderedChildren(orderedChildrenList.toArray(new String[orderedChildrenList.size()]));//TODO - verify !!!
        }

        JSONArray atrributesForAnalytics = feature.getAttributesForAnalytics();
        Set<String> attributeNamesSet = new HashSet<>();
        if (atrributesForAnalytics != null) {
            int attrLenghth = atrributesForAnalytics.length();
            for (int i = 0; i < attrLenghth; i++) {
                attributeNamesSet.add((String) atrributesForAnalytics.get(i));
            }
        }

        JSONObject configJson = feature.getConfiguration();
        if (configJson != null && configJson.length() > 0) {
            List<Attribute> attributes = new ArrayList<>();
            for (Object key : configJson.keySet()) {
                if (attributeNamesSet.contains(key)) {
                    shouldBeReported = true;
                    //based on you key types
                    String keyStr = (String) key;
                    Object value = configJson.get(keyStr);
                    attributes.add(new Attribute(keyStr, value));
                }
            }
            featureDetails.setAttributes(attributes.toArray(new Attribute[attributes.size()]));
        }

        if (!shouldBeReported) {
            featureDetails = null;
        }
        return featureDetails;
    }

    //recursive call to get all sub a feature and all its sub features
    private static List<Feature> getAllChildFeatures(Feature feature) {
        //Near future:add feature selectively - only if part of white list
        List<Feature> returnedFeatures = new ArrayList<>();
        returnedFeatures.add(feature);
        List<Feature> childrenFeatures = feature.getChildren();
        if (!childrenFeatures.isEmpty()) {
            int childrenFeaturesSize = childrenFeatures.size();
            for (int i = 0; i < childrenFeaturesSize; i++) {
                returnedFeatures.addAll(getAllChildFeatures(childrenFeatures.get(i)));
            }
        }
        return returnedFeatures;
    }

}
