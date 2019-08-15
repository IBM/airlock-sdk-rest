package com.ibm.app.services;

import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.rest.facades.ProductFacade;
import com.ibm.airlock.rest.facades.StringsFacade;
import com.ibm.airlock.rest.util.Strings;
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

@Path("/products")
@Api(value = "Strings", description = "Airlock translated strings API")
public class AirlockStringsService {

    @Context
    private ServletContext context;

    @PUT
    @Path("/{productInstanceId}/strings/{key}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Retrieves the translated string with the specified key" +
            " based on the locale of the application as specified in the pull function.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "String translated successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 404, message = "String key not found"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getStringByKey(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                   @ApiParam(value = "The key of the string to retrieve.", required = true) @PathParam("key") String key,
                                   @ApiParam(value = "An array of strings to replace any placeholders of the form [[[index]]].", name = "arguments") String[] arguments) {

        com.ibm.airlock.rest.common.Response response = StringsFacade.getStringByKey(productInstanceId, key, arguments);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/strings")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves all strings translated based on the locale of" +
            " the application as specified in the pull function." +
            " Placeholders will not be replaced with any values and will be returned in their original form.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieving all strings completed successfully", response = Map.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getAllStrings(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = StringsFacade.getAllStrings(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}
