package com.ibm.app.services;


import com.ibm.airlock.rest.facades.ProductActionsFacade;
import com.ibm.app.Constants;
import io.swagger.annotations.*;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/products")
@Api(value = "Actions", description = "Airlock product actions rest API")
public class AirlockProductActions {

    private final Logger logger = Logger.getLogger(AirlockProductActions.class.toString());
    @Context
    protected ServletContext context;

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/pull")
    @ApiOperation(value = "Downloads the latest rules and strings for a given product from the Airlock server. " +
            "In case there is no server connectivity, " +
            "Airlock will continue to use the existing rules and strings.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pull completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 412, message = "Wrong locale format"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response pull(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                         @ApiParam(value = "The current locale of the application. In case this locale differs from a previously supplied value," +
                                 " all of the cached data for this product will be cleared upon a successful pull.", required = true, example = "en_US") @QueryParam("locale") String locale) {
        com.ibm.airlock.rest.common.Response response = ProductActionsFacade.pull(productInstanceId, locale);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/pullAll")
    @ApiOperation(value = "Downloads the latest rules and strings for all products from the Airlock server." +
            "In case there is no server connectivity," +
            " Airlock will continue to use the existing rules and strings.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pull all products completed successfully", response = String.class),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response pullAllProducts() {
        com.ibm.airlock.rest.common.Response response = ProductActionsFacade.pullAll();
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/pull")
    @ApiOperation(value = "Returns the date of the last calculation performed in unix time format" +
            " (milliseconds from epoch).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting the last calculation date completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 412, message = "Wrong locale format"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getLastPull(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ProductActionsFacade.getLastPullTime(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/calculate")
    @ApiOperation(value = "Executes all rules and generates an updated configuration" +
            " for all features in the specified product based on the latest context.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calculate completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response calculate(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                              @ApiParam(value = "If true, the results will automatically replace the existing configuration. Otherwise, a sync call " +
                                      "is required in order to expose the latest results (via /products/{productId}/sync).", allowableValues = "true", required = true) @QueryParam("sync") Boolean isSync
    ) {
        com.ibm.airlock.rest.common.Response response = ProductActionsFacade.calculate(productInstanceId,
                isSync == null ? false : isSync);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/calculate")
    @ApiOperation(value = "Returns the date of the last calculation performed in unix time format (milliseconds from epoch).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting the last calculation date completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getLastCalculate(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ProductActionsFacade.getLastCalculateTime(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/sync")
    @ApiOperation(value = "Exposes the latest calculation results.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sync completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response sync(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ProductActionsFacade.sync(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/sync")
    @ApiOperation(value = "Returns the date of the last sync performed in unix time format (milliseconds from epoch).")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting the last sync date completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getLastSync(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ProductActionsFacade.getLastSyncTime(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/locale")
    @ApiOperation(value = "Returns locale of the last pull performed.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting the locale completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getLastPullLocale(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ProductActionsFacade.getLastPullLocale(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}
