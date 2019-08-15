package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.AnalyticsFacade;
import com.ibm.airlock.rest.facades.ServerLifecycleFacade;
import com.sun.net.httpserver.HttpExchange;

public class ServerLifecycleHandler extends BaseHandler {

    @Override
    public void get(HttpExchange request) {
        getPathParameter(4).ifPresent(lifecycleAction -> {
            if (lifecycleAction.equals("readiness")) {
                ServerLifecycleFacade.readiness();
                sendRespone(ServerLifecycleFacade.readiness(), request, Response.ContentType.TEXT_PLAIN);
            } else if (lifecycleAction.equals("preStop")) {
                sendRespone(ServerLifecycleFacade.preStop(), request, Response.ContentType.TEXT_PLAIN);
            } else {
                sendRespone(ServerLifecycleFacade.postStart(), request, Response.ContentType.TEXT_PLAIN);
            }
        });
    }
}
