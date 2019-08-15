package com.ibm.airlock.rest.facades;

import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.common.cache.PercentageManager;
import com.ibm.airlock.common.data.Feature;
import com.ibm.airlock.common.net.AirlockDAO;
import com.ibm.airlock.common.streams.AirlockStream;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.model.Stream;
import com.ibm.airlock.sdk.AbstractMultiProductManager;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.airlock.sdk.AirlockProductInstanceManager;
import com.ibm.airlock.sdk.cache.pref.FilePreferencesFactory;
import com.ibm.airlock.sdk.debug.AirlockDebugger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DebugFacade {

    private final static String MASTER_BRANCH_NAME = "master";
    private static final Logger logger = Logger.getLogger(DebugFacade.class.toString());

    public static Response clearCache(String productInstanceId
    ) {
        try {
            AirlockProductInstanceManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            String defaults = airlockProductManager.getDefaultFile();
            String appVersion = airlockProductManager.getAppVersion();
            String key = airlockProductManager.readAsStringByKey(com.ibm.airlock.common.util.Constants.SP_PRODUCT_KEY, "");

            //remove all instances of the same season
            Collection<AbstractMultiProductManager.ProductMetaData> products = AirlockMultiProductsManager.getInstance().getAllProducts();
            for (AbstractMultiProductManager.ProductMetaData productMD : products) {
                if (productMD.getSeasonId().equals(airlockProductManager.getSeasonId())) {
                    AirlockMultiProductsManager.getInstance().removeAirlockProductManager(productMD.getInstanceId());
                }
            }

            ProductFacade.init(FilePreferencesFactory.getAirlockCacheDirectory(),
                    defaults, key, appVersion, productInstanceId);


            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Set variant percentage Failed");
            return Response.status(500).entity("Set variant percentage Failed").build();
        }
    }


    //("/{productInstanceId}/features")
    public static Response getFeatureStatuses(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            Map<String, Feature> features = airlockProductManager.getSyncFeatureList().getFeatures();
            List<com.ibm.airlock.rest.model.Feature> array = new ArrayList<>();

            if (features != null) {
                for (com.ibm.airlock.common.data.Feature curFeature : features.values()) {
                    // skip root element
                    if (!curFeature.getName().equals("ROOT")) {
                        array.add(new com.ibm.airlock.rest.model.Feature(curFeature));
                    }
                }
            }
            return Response.status(200).entity(array).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Get feature Failed");
            return Response.status(500).entity("Get feature failed").build();
        }
    }

    //@Path("/{productId}/branches")
    public static Response getProductBranches(String productInstanceId) {
        try {
            AirlockDebugger airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockDebugger(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            List<String> result = new ArrayList();

            // add master as a first branch
            result.add(MASTER_BRANCH_NAME);

            JSONArray branches = airlockProductManager.getDevelopmentBranches();
            int length = 0;
            if (branches != null) {
                length = branches.length();
            }
            for (int i = 0; i < length; i++) {
                JSONObject branchItem = branches.getJSONObject(i);
                String name = branchItem.optString("name");
                if (!name.isEmpty()) {
                    result.add(name);
                }
            }
            return Response.status(200).entity(result).build();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Get branches Failed");
            return Response.status(500).entity("Get branches Failed").build();
        }
    }

    //@Path("/{productId}/branch")
    public static Response getSelectedBranch(String productInstanceId) {
        try {
            AirlockDebugger airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockDebugger(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            if (airlockProductManager.getSelectedDevelopmentBranch() == null ||
                    airlockProductManager.getSelectedDevelopmentBranch().isEmpty()) {
                return Response.status(200).entity(MASTER_BRANCH_NAME).build();
            } else {
                return Response.status(200).entity(
                        airlockProductManager.getSelectedDevelopmentBranch()).build();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Get branch Failed");
            return Response.status(500).entity("Get branch Failed").build();
        }
    }

    //@Path("/{productId}/branches/{branchName}")
    public static Response putProductBranch(String productInstanceId, String branchName) {
        try {
            AirlockDebugger airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockDebugger(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            //  if a given brach is master
            if (branchName.toLowerCase(Locale.getDefault()).equals(MASTER_BRANCH_NAME)) {
                branchName = "";
            }

            airlockProductManager.setDevelopmentBranch(branchName);
            List<String> result = new ArrayList();
            JSONArray branches = airlockProductManager.getDevelopmentBranches();
            int length = 0;
            if (branches != null) {
                length = branches.length();
            }
            for (int i = 0; i < length; i++) {
                JSONObject branchItem = branches.getJSONObject(i);
                String name = branchItem.optString("name");
                if (!name.isEmpty()) {
                    result.add(name);
                }
            }
            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Put branch Failed");
            return Response.status(500).entity("Put branch failed").build();
        }
    }

    //@Path("/{productInstanceId}/percentage")
    public static Response setDevicePercentageRange4Feature(String productInstanceId,
                                                            String sectionName,
                                                            String itemName,
                                                            final String inRange) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            PercentageManager.Sections section = PercentageManager.Sections.valueOf(sectionName.trim().toUpperCase());

            airlockProductManager.setDeviceInItemPercentageRange(section, itemName, Boolean.parseBoolean(inRange));
            if (section == PercentageManager.Sections.STREAMS){
                airlockProductManager.getStreamsManager().updateStreamsEnablement();
            }
            return Response.status(200).entity("Setting the device percentage range is done successfully").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Set feature percentage Failed");
            return Response.status(500).entity("Set feature percentage Failed").build();
        }
    }

    //@GET
    //@Path("/{productInstanceId}/percentage")
    public static Response isDeviceInFeaturePercentageRange(String productInstanceId,
                                                            String sectionName,
                                                            String featureName) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            PercentageManager.Sections section = PercentageManager.Sections.valueOf(sectionName.trim().toUpperCase());

            return Response.status(200).entity(airlockProductManager.isDeviceInItemPercentageRange(section, featureName)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Getting fthe device percentage range failed");
            return Response.status(500).entity("Getting fthe device percentage range failed").build();
        }
    }

    //@GET
    //@Path("/{productInstanceId}/experiment")
    public static Response getExperiment(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            String experiments = airlockProductManager.readAsStringByKey(com.ibm.airlock.common.util.Constants.JSON_FIELD_DEVICE_EXPERIMENTS_LIST, "{}");

            String outExperiment = "{}";

            JSONObject jsonExperiments = new JSONObject(experiments);

            if (jsonExperiments == null) {
                jsonExperiments = new JSONObject();
            }

            JSONArray experimentsArray = jsonExperiments.optJSONArray("experiments");

            if (experimentsArray == null) {
                experimentsArray = new JSONArray();
            }

            int length = experimentsArray.length();

            for (int i = 0; i < length; i++) {
                JSONObject experimentItem = experimentsArray.optJSONObject(i);
                if (experimentItem != null) {
                    if (experimentItem.optBoolean("isON")) {
                        outExperiment = experimentItem.toString();
                    }
                }
            }

            return Response.status(200).entity(outExperiment).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Get experiment Failed");
            return Response.status(500).entity("Get experiment failed").build();
        }
    }


    //@GET
    //@Path("/{productInstanceId}/experiments")
    public static Response getExperiments(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            String experiments = airlockProductManager.readAsStringByKey(com.ibm.airlock.common.util.Constants.JSON_FIELD_DEVICE_EXPERIMENTS_LIST, "{}");

            return Response.status(200).entity(experiments).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Get experiment Failed");
            return Response.status(500).entity("Get experiment failed").build();
        }
    }

    //@GET
    //@Path("/{productInstanceId}/streams/")
    public static Response getStreams(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            List<Stream> streams = new ArrayList<>();
            List<AirlockStream> inputStreams = airlockProductManager.getStreamsManager().getStreams();
            if (inputStreams != null) {
                for (AirlockStream stream : inputStreams) {
                    if (stream != null) {
                        streams.add(new Stream(stream));
                    }
                }
            }

            return Response.status(200).entity(streams).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Get Streams Failed");
            return Response.status(500).entity("Get Streams failed: " + e.getMessage()).build();
        }
    }

    //@PUT
    //@Path("/{productInstanceId}/streams/{streamId}/clearTrace")
    public static Response clearStreamsTrace(String productInstanceId, String streamId) {
        try {

            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);

            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            AirlockStream stream = airlockProductManager.getStreamsManager().getStreamByName(streamId);

            stream.clearTrace();

            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get analytics data failed").build();
        }
    }

    //@PUT
    //@Path("/{productInstanceId}/streams/{streamId}/reset")
    public static Response resetStreams(String productInstanceId, String streamId, List events) {
        try {

            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);

            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            AirlockStream stream = airlockProductManager.getStreamsManager().getStreamByName(streamId);

            stream.clearProcessingData();

            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get analytics data failed").build();
        }
    }


    // @PUT
    // @Path("/{productInstanceId}/streams/stream/{streamId}/actions/{actionId}")
    public static Response runActionOnStream(String productInstanceId,
                                             String streamId, String action, Boolean suspend) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);

            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            AirlockStream stream = airlockProductManager.getStreamsManager().getStreamByName(streamId);
            if (stream != null) {
                switch (action) {
                    case "reset":
                        stream.clearProcessingData();
                        break;
                    case "clearTrace":
                        stream.clearTrace();
                        break;
                    case "suspend":
                        stream.setProcessingSuspended(suspend);
                        break;
                }
            }

            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get analytics data failed").build();
        }
    }


    //@GET
    //@Path("/{productInstanceId}/responsive-mode/")
    public static Response getResponsiveMode(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            return Response.status(200).entity(airlockProductManager.getDataProviderType().name()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Get responsive mode failed");
            return Response.status(500).entity("Get responsive mode failed").build();
        }
    }

    //@PUT
    //@Path("/{productInstanceId}/responsive-mode/")
    public static Response setResponsiveMode(String productInstanceId, String responsiveMode) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            airlockProductManager.setDataProviderType(AirlockDAO.DataProviderType.valueOf(responsiveMode.toUpperCase(Locale.ENGLISH)));
            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.severe("Set responsive mode failed");
            return Response.status(500).entity("Set responsive mode failed").build();
        }
    }
}
