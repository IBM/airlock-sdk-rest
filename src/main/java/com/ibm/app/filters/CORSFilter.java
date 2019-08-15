package com.ibm.app.filters;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
@Priority(value = 1)
public class CORSFilter implements ContainerResponseFilter {

	public static final int CORS_FILTER_MAX_AGE_IN_SECS = 12 * 60 * 60;
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		MultivaluedMap<String, Object> headers = responseContext.getHeaders();
		
		// TODO: narrow to console domain
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		headers.add("Access-Control-Allow-Credentials", "true");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		headers.add("Access-Control-Max-Age", CORS_FILTER_MAX_AGE_IN_SECS);
	}
}
