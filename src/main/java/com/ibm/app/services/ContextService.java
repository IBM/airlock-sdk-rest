package com.ibm.app.services;

import com.google.gson.Gson;
import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.common.engine.StateFullContext;
import com.ibm.airlock.rest.facades.ContextFacade;
import com.ibm.airlock.rest.facades.ProductFacade;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.app.Constants;
import io.swagger.annotations.*;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("products")
@Api(value = "Context", description = "Airlock Context API")
public class ContextService {

    private final Logger logger = Logger.getLogger(ContextService.class.toString());
    @Context
    private ServletContext context;


    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/context")
    @ApiOperation(value = "Updates the cross-product contextual information, shared by all products." +
            " This context will be used in all following calculate operations until changed. The specified context will be merged with the existing context," +
            " overriding any existing fields.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update completed successfully", response = String.class),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response updateSharedContext(@ApiParam(value = "A JSON object containing shared cross-product contextual information that can be used in Airlock rules. Can contain nested values. " +
            "Product specific context fields (via /products/{productId}/context)" +
            " will take precedence over shared context fields of the same name.", required = true, name = "sharedContext") Map<String, Object> sharedContext) {
        com.ibm.airlock.rest.common.Response response = ContextFacade.updateSharedContext(new Gson().toJson(sharedContext));
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/context")
    @ApiOperation(value = "Removes the cross-product contextual information.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Remove completed successfully", response = String.class),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response clearSharedContext() {
        com.ibm.airlock.rest.common.Response response = ContextFacade.clearSharedContext();
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/context")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves the current cross-product contextual information.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve completed successfully", response = Map.class),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response getSharedContext() {
        com.ibm.airlock.rest.common.Response response = ContextFacade.getSharedContext();
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/context")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Updates the contextual information of the specified product." +
            " This context will be used in all following calculate operations until changed. " +
            " The specified context will be merged with the existing context, overriding any existing fields.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response updateProductContext(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                         @ApiParam(value = " If true, the specified context will be used as is, disregarding any existing values. " +
                                                 "Otherwise, the specified context will be merged with the existing context, overriding any existing fields.", required = true) @QueryParam("clearPreviousContext") boolean clearPreviousContext,
                                         @ApiParam(value = "A JSON object containing all product specific contextual information that can be used in Airlock rules. Can contain nested values." +
                                                 " Fields of this context will take precedence over any shared context fields of the same name.", required = true, name = "context",
                                                 examples = @Example(value = {
                                                         @ExampleProperty(mediaType = MediaType.APPLICATION_JSON, value = Constants.CONTEXT_SAMPLE)})) Map<String, Object> context) {
        com.ibm.airlock.rest.common.Response response = ContextFacade.updateProductContext(productInstanceId,
                new Gson().toJson(context).toString(),clearPreviousContext);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{productInstanceId}/context")
    @ApiOperation(value = "Removes the specific product contextual information.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Remove completed successfully", response = String.class),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response clearContext(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ContextFacade.clearContext(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/context")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves the current context of the specified product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve completed successfully", response = Map.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response getProductContext(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ContextFacade.getProductContext(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/context/last-calculated")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves the merged shared and product specific context that was used in the last calculation operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve completed successfully",  response = Map.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response getLastContext(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ContextFacade.getLastContext(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/context/current")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves the current merged shared and product specific context.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve completed successfully", response = Map.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response getCurrentContext(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = ContextFacade.getCurrentContext(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}
