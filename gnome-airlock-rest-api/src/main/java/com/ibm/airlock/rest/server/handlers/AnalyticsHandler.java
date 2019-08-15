package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.AnalyticsFacade;
import com.sun.net.httpserver.HttpExchange;

public class AnalyticsHandler extends BaseHandler {

    @Override
    public void get(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            if (getPathParameter(7).isPresent()) {
                getPathParameter(7).ifPresent(featureName -> {
                    sendRespone(AnalyticsFacade.getFeatureAttributesForAnalytics(instanceId, featureName), request,
                            Response.ContentType.APPLICATION_JSON);
                });
            } else {
                sendRespone(AnalyticsFacade.getAllFieldsForAnalytics(instanceId), request,
                        Response.ContentType.APPLICATION_JSON);
            }
        });
    }
}
