package com.ibm.app.services;

import com.ibm.airlock.rest.facades.UserGroupsFacade;
import com.ibm.app.Constants;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.util.List;

@Path("/products")
@Api(value = "User Groups", description = "Airlock sdk user groups API")
public class UserGroupsService {


    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{productInstanceId}/usergroups")
    @ApiOperation(value = "Updates the list of user groups for the specified product with the specified list." +
            " All previously set user groups will be overridden with this list, unless the product has the same user groups list.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update completed successfully", response = String.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response updateUserGroupsToProduct(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId,
                                              @ApiParam(value = "An array of strings containing the user groups to set.") List<String> userGroups) {
        com.ibm.airlock.rest.common.Response response = UserGroupsFacade.updateUserGroupsToProduct(productInstanceId, userGroups);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }


    @GET
    @Path("/{productInstanceId}/usergroups")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves all user groups that were set for the specified product.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve completed successfully", response = List.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getUserGroups(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = UserGroupsFacade.getUserGroups(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }

    @GET
    @Path("/{productInstanceId}/usergroups/all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieves all user groups exists for the specified product on the server.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieve completed successfully", response = List.class),
            @ApiResponse(code = 400, message = "Product not initialized"),
            @ApiResponse(code = 500, message = "Internal error")})

    public Response getAllUserGroups(@ApiParam(value = Constants.PRODUCT_ID_PARAM, example = Constants.PRODUCT_ID_PARAM_SAMPLE) @PathParam("productInstanceId") String productInstanceId) {
        com.ibm.airlock.rest.common.Response response = UserGroupsFacade.getAllUserGroups(productInstanceId);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}
