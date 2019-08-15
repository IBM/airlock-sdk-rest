package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.StringsFacade;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class StringsHandler extends BaseHandler {


    @Override
    public void put(HttpExchange request) {

        //not valid should be empty array
        if (body.isEmpty()) {
            sendRespone(Response.status(415).entity("").build(), request, Response.ContentType.TEXT_PLAIN);
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(body);
            List<String> list = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
            String[] arguments = list.toArray(new String[list.size()]);

            getPathParameter(4).ifPresent(instanceId -> {
                getPathParameter(6).ifPresent(key -> {
                    sendRespone(StringsFacade.getStringByKey(instanceId, key, arguments), request, Response.ContentType.TEXT_PLAIN);
                });
            });
        }catch (Exception e){
            sendRespone(Response.status(400).entity(e.getMessage()).build(), request, Response.ContentType.TEXT_PLAIN);
        }
    }


    @Override
    public void get(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            sendRespone(StringsFacade.getAllStrings(instanceId), request, Response.ContentType.APPLICATION_JSON);
        });
    }

    static String[] getStringArray(String... array) {
        return array;
    }
}
