package com.ibm.airlock.rest.facades;

import com.google.gson.Gson;
import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamsFacade {

    private static final Logger logger = Logger.getLogger(StreamsFacade.class.toString());

    public static Response runStreams(String productInstanceId, String streamId, List events) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);

            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            String[] streamsFilter = null;
            if (streamId != null && !streamId.isEmpty()) {
                streamsFilter = new String[]{streamId};
            }
            JSONArray eventsArray = null;
            if (events != null) {
                eventsArray = new JSONArray(events);
            }
            airlockProductManager.getStreamsManager().calculateAndSaveStreams(eventsArray, true, streamsFilter);

            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("run stream failed").build();
        }
    }

    public static Response addEvents(String productInstanceId, List events) {
        try {

            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);

            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            airlockProductManager.getStreamsManager().calculateAndSaveStreams(new JSONArray(events), false, null);

            return Response.status(200).entity("").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("add stream event failed").build();
        }
    }

    public static Response getStreamsResults(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            return Response.status(200).entity(new Gson().fromJson(airlockProductManager.getStreamsSummary(),Map.class)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get stream data failed").build();
        }
    }
}
