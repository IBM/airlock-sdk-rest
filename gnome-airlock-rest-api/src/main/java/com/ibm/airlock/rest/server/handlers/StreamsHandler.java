package com.ibm.airlock.rest.server.handlers;

import com.google.gson.Gson;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.StreamsFacade;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

public class StreamsHandler extends BaseHandler {


    private static String STREAM_ID = "streamId";

    @Override
    public void put(HttpExchange request) {

        String streamId = queryParameters.get(STREAM_ID);
        List events = new Gson().fromJson(body, List.class);

        getPathParameter(4).ifPresent(instanceId -> {
            if (getPathParameter(6).isPresent()) {
                getPathParameter(6).ifPresent(streamAction -> {
                    if (streamAction.equals("run")) {
                        sendRespone(StreamsFacade.runStreams(instanceId, streamId, events), request, Response.ContentType.APPLICATION_JSON);
                    } else if (streamAction.equals("addEvents")) {
                        sendRespone(StreamsFacade.addEvents(instanceId, events), request, Response.ContentType.APPLICATION_JSON);
                    } else {
                        sendNotFoundServiceError(request);
                    }
                });
            } else {
                sendRespone(StreamsFacade.getStreamsResults(instanceId), request, Response.ContentType.APPLICATION_JSON);
            }
        });
    }

    @Override
    public void get(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            sendRespone(StreamsFacade.getStreamsResults(instanceId), request, Response.ContentType.APPLICATION_JSON);
        });
    }
}
