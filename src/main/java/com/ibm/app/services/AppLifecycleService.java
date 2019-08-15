package com.ibm.app.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.logging.Logger;

@Path("/appLifecycle")
@Api(value = "AppLifecycle", description = "appLifecycle")
public class AppLifecycleService {


	private final Logger logger = Logger.getLogger(AppLifecycleService.class.toString());

	@GET
	@Path("/postStart")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request")})
	public Response postStart() {
		return Response.status(200).build();
	}

	@GET
	@Path("/preStop")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request")})
	public Response preStop() {
		return Response.status(200).build();
	}

	@GET
	@Path("/readiness")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request")})
	public Response readiness() {
		return Response.status(200).build();
	}

	@GET
	@Path("/liveness")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request")})
	public Response liveness() {
		return Response.status(200).build();
	}
}
