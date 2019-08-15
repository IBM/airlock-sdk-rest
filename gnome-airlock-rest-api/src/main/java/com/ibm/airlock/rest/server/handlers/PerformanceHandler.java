package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.util.StatisticsManager;
import com.sun.net.httpserver.HttpExchange;

public class PerformanceHandler extends BaseHandler {

    private static final String SERVICE_NAME = "serviceName";

    @Override
    public void get(HttpExchange request) {
        final String serviceName = queryParameters.get(SERVICE_NAME);

        String result = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body>\n" +
                "<pre>\n" +
                      StatisticsManager.getHandlerStatistics(serviceName).toString(1)+"\n"+
                "</pre>\n" +
                "</body>\n" +
                "</html>";
        sendRespone(Response.status(200).entity(result).build(), request,
                Response.ContentType.TEXT_HTML);
    }
}