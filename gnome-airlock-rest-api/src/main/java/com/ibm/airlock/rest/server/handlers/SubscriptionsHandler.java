package com.ibm.airlock.rest.server.handlers;

import com.google.gson.Gson;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.NotificationFacade;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public class SubscriptionsHandler extends BaseHandler {

    @Override
    public void put(HttpExchange request) {

        getPathParameter(3).ifPresent(subscriptionId -> {
            List<Map<String, Object>> notificationsList = new Gson().fromJson(body, List.class);
            sendRespone(NotificationFacade.notificationHandler(notificationsList),
                    request, Response.ContentType.APPLICATION_JSON);
        });
    }
}
