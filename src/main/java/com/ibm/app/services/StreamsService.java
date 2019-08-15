package com.ibm.app.services;

import com.ibm.airlock.rest.facades.StreamsFacade;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/products")
@Api(value = "Streams", description = "Airlock streams API")
public class StreamsService {

    @PUT
    @Path("/{productInstanceId}/streams/run")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Calculates all streams according to the events sent")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve feature completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response runStreams(@ApiParam(value = com.ibm.app.Constants.PRODUCT_ID_PARAM, example = com.ibm.app.Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId, @QueryParam("streamId") String streamId, List events) {
        com.ibm.airlock.rest.common.Response response = StreamsFacade.runStreams(productInstanceId, streamId, events);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @PUT
    @Path("/{productInstanceId}/streams/addEvents")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Add events to the streams processing stack")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Add event completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response addEvents(@ApiParam(value = com.ibm.app.Constants.PRODUCT_ID_PARAM, example = com.ibm.app.Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId, List events) {
        com.ibm.airlock.rest.common.Response response = StreamsFacade.addEvents(productInstanceId, events);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/streams/results")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves the last streams calculation results")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve the last streams calculation completed successfully", response = Map.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getStreamsResults(@ApiParam(value = com.ibm.app.Constants.PRODUCT_ID_PARAM, example = com.ibm.app.Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = StreamsFacade.getStreamsResults(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}
