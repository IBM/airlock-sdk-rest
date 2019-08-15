package com.ibm.app.services;

import com.ibm.airlock.rest.facades.DebugFacade;
import com.ibm.airlock.rest.model.Experiment;
import com.ibm.airlock.rest.model.ExperimentList;
import com.ibm.airlock.rest.model.Feature;
import com.ibm.airlock.rest.model.Stream;
import com.ibm.app.Constants;
import io.swagger.annotations.*;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/products")
@Api(value = "Debug", hidden = true, description = "Airlock sdk management API for debug purpose (internal API)")
public class DebugAirlockProduct {
    @Context
    private ServletContext context;

    @Inject
    Provider<MonitoringStatistics> monitoringStatisticsProvider;

    @Path("/statistic")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Retrieves the server statistic data.", response = String.class)
    public Response statistic() {
        final MonitoringStatistics snapshot
                = monitoringStatisticsProvider.get();


        final TimeWindowStatistics totaltimeWindowStatistics
                = snapshot.getRequestStatistics()
                .getTimeWindowStatistics().get(0L);

        final TimeWindowStatistics timeWindowStatistics
                = snapshot.getRequestStatistics()
                .getTimeWindowStatistics().get(60000L);

        String report = "Global Statistic:\n"
                + "---------------------------------------- \n"
                + "total requests count: " + totaltimeWindowStatistics.getRequestCount() + "\n"
                + "total average request processing [ms]: " + totaltimeWindowStatistics.getAverageDuration() + "\n\n"
                + "Per last minute Statistic\n"
                + "---------------------------------------- \n"
                + "requests count: " + timeWindowStatistics.getRequestCount() + "\n"
                + "average request processing [ms]: " + timeWindowStatistics.getAverageDuration() + "\n"
                + "requests per second :" + timeWindowStatistics.getRequestsPerSecond();

        return Response.status(200).entity(report).build();
    }

    @GET
    @Path("/{productInstanceId}/features")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves all features of the specified product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve completed successfully", response = Feature.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getFeatureStatuses(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.getFeatureStatuses(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/branches")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves list of branch names existing on this product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve completed successfully", response = List.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getProductBranches(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.getProductBranches(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/branch")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Retrieves the current branch name is being used for this product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve branch name completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 404, message = "Branch not found"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getSelectedBranch(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.getSelectedBranch(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @PUT
    @Path("/{productInstanceId}/branches/{branchName}")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Sets the current branch to be used on this product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Setting current branch completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response putProductBranch(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId, @PathParam("branchName") String branchName) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.putProductBranch(productInstanceId, branchName);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @PUT
    @Path("/{productInstanceId}/percentage")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Sets the device to be inside the feature percentage range or outside. The method is applicable for features, experiments,variant and streams")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Set device in the feature percentage range completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response setDevicePercentageRange4Feature(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                                     @ApiParam(value = "The section of the item updated: could be features/experiments/streams", example = "features", required = true) @QueryParam("section") String section,
                                                     @ApiParam(value = "The name of the item updated", example = "namespace.feature1", required = true) @QueryParam("itemName") String itemName,
                                                     @ApiParam(value = "Specifies whether the device in the feature percentage range or out.", name = "inRange"
                                                     ) final String inRange) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.setDevicePercentageRange4Feature(productInstanceId, section, itemName, inRange);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @GET
    @Path("/{productInstanceId}/percentage")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Returns whether the device inside the feature percentage range or outside. The method is applicable for features, experiments,variant and streams")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get wheather device in the feature percentage range  completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response isDeviceInFeaturePercentageRange(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                                     @ApiParam(value = "The section of the item updated: could be features/experiments/streams", example = "features", required = true) @QueryParam("section") String section,
                                                     @ApiParam(value = "The name of the item updated", example = "namespace.feature1", required = true) @QueryParam("itemName") String itemName) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.isDeviceInFeaturePercentageRange(productInstanceId, section, itemName);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/experiment")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves current experiment info.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve current experiment  completed successfully", response = Experiment.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getExperiment(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.getExperiment(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @GET
    @Path("/{productInstanceId}/experiments")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves the existing experiments info.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve existing experiments completed successfully", response = ExperimentList.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getExperiments(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.getExperiments(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/streams")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Gets all streams for this product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all streams completed successfully", response = Stream.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getStreams(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.getStreams(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @PUT
    @Path("/{productInstanceId}/streams/{streamId}/actions/{actionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Apply stream action on specific stream, the available actions are: [reset], [clearTrace], [suspend]")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Apply stream action completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response runActionOnStream(@ApiParam(value = com.ibm.app.Constants.PRODUCT_ID_PARAM, example = com.ibm.app.Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                      @PathParam("streamId") String streamId, @PathParam("actionId") String action, @QueryParam("doSuspend") Boolean suspend) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.runActionOnStream(productInstanceId, streamId, action, suspend);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @DELETE
    @Path("/{productInstanceId}/cache")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Clears the cached data for specific product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Clear cached data completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response clearCache(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId
    ) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.clearCache(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/responsive-mode/")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Gets product responsive mode streams for this product. DIRECT_MODE or CACHED_MODE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get responsive mode completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getResponsiveMode(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.getResponsiveMode(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @PUT
    @Path("/{productInstanceId}/responsive-mode/")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Update product responsive mode streams for this product. DIRECT_MODE or CACHED_MODE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update responsive mode completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response setResponsiveMode(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                      @ApiParam(value = "Specifies whether the device in the feature percentage range or out.", name = "responsiveMode"
                                      ) String responsiveMode) {
        com.ibm.airlock.rest.common.Response response = DebugFacade.setResponsiveMode(productInstanceId, responsiveMode);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}

