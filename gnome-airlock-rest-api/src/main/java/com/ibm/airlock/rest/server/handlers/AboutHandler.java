package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.AnalyticsFacade;
import com.ibm.airlock.rest.server.GnomeAirlockRest;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;

public class AboutHandler extends BaseHandler {

    @Override
    public void get(HttpExchange request) {
        sendRespone(Response.status(200).entity("Gnome Airlock Rest API (version:1.0.0)").build(), request,
                Response.ContentType.TEXT_HTML);
    }
}
