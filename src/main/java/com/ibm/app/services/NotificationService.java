package com.ibm.app.services;

import com.ibm.airlock.rest.facades.NotificationFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/notify")
@Api(value = "Notification", description = "Handles update notification for Airlock Rest API into shared context from VAS")
public class NotificationService {

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Update notification from VAS into Airlock shared context")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal error")})
    public Response mergeVASNotificationIntoContext(List<Map<String, Object>> notificationsList) {
        com.ibm.airlock.rest.common.Response response =
                NotificationFacade.notificationHandler(notificationsList);
        return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}
