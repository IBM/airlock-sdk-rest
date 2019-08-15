package com.ibm.app.services;

import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.common.data.Feature;
import com.ibm.airlock.common.util.Constants;
import com.ibm.airlock.rest.facades.AnalyticsFacade;
import com.ibm.airlock.rest.facades.StringsFacade;
import com.ibm.airlock.rest.model.AnalyticsData;
import com.ibm.airlock.rest.model.Attribute;
import com.ibm.airlock.rest.model.FeatureAnalytics;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import io.swagger.annotations.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/products")
@Api(value = "Analytics", description = "Airlock analytics API")
public class AnalyticsService {

    @Context
    private ServletContext context;

    @GET
    @Path("/{productInstanceId}/analytics")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves all analytics data marked to be sent in the Control Center including context fields" +
            " and feature information.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting analytics data completed successfully", response = AnalyticsData.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getAllFieldsForAnalytics(@ApiParam(value = com.ibm.app.Constants.PRODUCT_ID_PARAM, example = com.ibm.app.Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = AnalyticsFacade.getAllFieldsForAnalytics(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/analytics/features/{featureName}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves all analytics data marked to be sent in the Control Center for" +
            " the specified feature.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting analytics data completed successfully", response = FeatureAnalytics.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 404, message = "Feature not found"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getFeatureAttributesForAnalytics(@ApiParam(value = com.ibm.app.Constants.PRODUCT_ID_PARAM, example = com.ibm.app.Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                                     @ApiParam(value = "The name of the feature to retrieve analytics data for.") @PathParam("featureName") String featureName) {
        com.ibm.airlock.rest.common.Response response = AnalyticsFacade.getFeatureAttributesForAnalytics(productInstanceId, featureName);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}
