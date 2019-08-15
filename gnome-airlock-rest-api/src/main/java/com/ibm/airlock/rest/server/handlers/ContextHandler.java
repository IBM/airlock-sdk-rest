package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.ContextFacade;
import com.ibm.airlock.rest.facades.FeatureFacade;
import com.ibm.airlock.rest.facades.ProductFacade;
import com.sun.net.httpserver.HttpExchange;

public class ContextHandler extends BaseHandler {


    private static final String CLEAR_PREVIOUS_CONTEXT = "clearPreviousContext";

    @Override
    public void get(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            if (getPathParameter(5).isPresent()) {
                if(getPathParameter(6).isPresent()){
                    getPathParameter(6).ifPresent(contextType -> {
                        if (contextType.equals("current")) {
                            sendRespone(ContextFacade.getCurrentContext(instanceId), request,
                                    Response.ContentType.APPLICATION_JSON);
                        } else if (contextType.equals("last-calculated")) {
                            sendRespone(ContextFacade.getLastContext(instanceId), request,
                                    Response.ContentType.APPLICATION_JSON);
                        } else {
                            BaseHandler.sendNotFoundServiceError(request);
                            return;
                        }
                    });
                }else{
                    sendRespone(ContextFacade.getProductContext(instanceId), request,
                            Response.ContentType.APPLICATION_JSON);
                }
            } else {
                sendRespone(ContextFacade.getSharedContext(), request,
                        Response.ContentType.APPLICATION_JSON);
            }
        });

    }


    @Override
    public void put(HttpExchange request) {
        final Boolean clearPreviousContext = Boolean.parseBoolean(queryParameters.get(CLEAR_PREVIOUS_CONTEXT));

        getPathParameter(4).ifPresent(instanceId -> {
            if (getPathParameter(5).isPresent()) {
                sendRespone(ContextFacade.updateProductContext(instanceId, body, clearPreviousContext), request,
                        Response.ContentType.TEXT_PLAIN);
            } else {
                sendRespone(ContextFacade.updateSharedContext(body), request,
                        Response.ContentType.TEXT_PLAIN);
            }
        });
    }

    @Override
    public void delete(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            if (getPathParameter(5).isPresent()) {
                sendRespone(ContextFacade.clearContext(instanceId), request,
                        Response.ContentType.TEXT_PLAIN);
            } else {
                sendRespone(ContextFacade.clearSharedContext(), request,
                        Response.ContentType.TEXT_PLAIN);
            }
        });
    }

}
