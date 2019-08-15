package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.UserGroupsFacade;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserGroupsHandler extends BaseHandler {

    @Override
    public void put(HttpExchange request) {
        JSONArray jsonObj = null;
        try {
            jsonObj = new JSONArray(body);
        } catch (Exception e) {
            sendRespone(Response.status(400).entity(e.getMessage()).build(), request, Response.ContentType.TEXT_PLAIN);
            return;
        }
        List<String> userGroups = new ArrayList<>();
        if (jsonObj != null) {
            for (int i = 0; i < jsonObj.length(); i++) {
                userGroups.add(jsonObj.getString(i));
            }
        }
        getPathParameter(4).ifPresent(instanceId -> {
            sendRespone(UserGroupsFacade.updateUserGroupsToProduct(instanceId, userGroups), request, Response.ContentType.TEXT_PLAIN);
        });
    }

    @Override
    public void get(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            if (getPathParameter(6).isPresent()) {
                sendRespone(UserGroupsFacade.getAllUserGroups(instanceId), request, Response.ContentType.APPLICATION_JSON);
            } else {
                sendRespone(UserGroupsFacade.getUserGroups(instanceId), request, Response.ContentType.APPLICATION_JSON);
            }
        });
    }
}
