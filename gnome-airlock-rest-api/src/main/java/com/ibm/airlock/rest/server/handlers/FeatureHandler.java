package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.ContextFacade;
import com.ibm.airlock.rest.facades.FeatureFacade;
import com.ibm.airlock.rest.facades.ProductActionsFacade;
import com.ibm.airlock.rest.model.Feature;
import com.sun.net.httpserver.HttpExchange;

public class FeatureHandler extends BaseHandler {


    @Override
    public void get(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            getPathParameter(6).ifPresent(featureName -> {
                if (getPathParameter(7).isPresent()) {
                    sendRespone(FeatureFacade.getFeatureChildren(instanceId, featureName), request,
                            Response.ContentType.APPLICATION_JSON);
                } else {
                    sendRespone(FeatureFacade.getFeatureByName(instanceId, featureName), request,
                            Response.ContentType.APPLICATION_JSON);
                }
            });
        });
    }
}
