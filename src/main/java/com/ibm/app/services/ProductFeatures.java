package com.ibm.app.services;


import com.ibm.airlock.rest.facades.FeatureFacade;
import com.ibm.airlock.rest.model.Feature;
import com.ibm.app.Constants;
import io.swagger.annotations.*;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Features", description = "Airlock product features rest API")
public class ProductFeatures {
    private final Logger logger = Logger.getLogger(ProductFeatures.class.toString());
    @Context
    private ServletContext context;

    @GET
    @Path("/{productInstanceId}/features/{featureName}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves the feature with the specified name.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve feature completed successfully", response = Feature.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getFeatureByName(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                     @ApiParam(value = "The name of the feature to retrieve.", required = true) @PathParam("featureName") String featureName) {
        com.ibm.airlock.rest.common.Response response =
                FeatureFacade.getFeatureByName(productInstanceId, featureName);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @GET
    @Path("/{productInstanceId}/features/{featureName}/children")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves the children of the specified feature.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve feature's children completed successfully", response = Feature.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getFeatureChildren(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                       @ApiParam(value = "The name of the feature to retrieve the children for.", required = true) @PathParam("featureName") String featureName) {
        com.ibm.airlock.rest.common.Response response =
                FeatureFacade.getFeatureChildren(productInstanceId, featureName);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

}
