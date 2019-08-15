package com.ibm.airlock.rest.server.handlers;

import com.google.gson.Gson;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.DebugFacade;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

public class DebugHandler extends BaseHandler {

    private static String DO_SUSPEND = "doSuspend";
    private static final String SECTION = "section";
    private static final String ITEM_NAME = "itemName";


    @Override
    public void delete(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            sendRespone(DebugFacade.clearCache(instanceId), request,
                    Response.ContentType.TEXT_PLAIN);
        });
    }

    @Override
    public void get(HttpExchange request) {

        String section = queryParameters.get(SECTION);
        String itemName = queryParameters.get(ITEM_NAME);

        getPathParameter(4).ifPresent(instanceId -> {
            getPathParameter(5).ifPresent(action -> {
                if (action.equals(Action.PERCENTAGE.action())) {
                    sendRespone(DebugFacade.isDeviceInFeaturePercentageRange(instanceId, section, itemName), request,
                            Response.ContentType.APPLICATION_JSON);
                } else if (action.equals(Action.BRANCHS.action())) {
                    sendRespone(DebugFacade.getProductBranches(instanceId), request,
                            Response.ContentType.APPLICATION_JSON);
                } else if (action.equals(Action.BRANCH.action())) {
                    sendRespone(DebugFacade.getSelectedBranch(instanceId), request,
                            Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.EXPERIMENT.action())) {
                    sendRespone(DebugFacade.getExperiment(instanceId), request,
                            Response.ContentType.APPLICATION_JSON);
                } else if (action.equals(Action.EXPERIMENTS.action())) {
                    sendRespone(DebugFacade.getExperiments(instanceId), request,
                            Response.ContentType.APPLICATION_JSON);
                } else if (action.equals(Action.STREAMS.action())) {
                    sendRespone(DebugFacade.getStreams(instanceId), request,
                            Response.ContentType.APPLICATION_JSON);
                } else if (action.equals(Action.RESPONSIVE_MODE.action())) {
                    sendRespone(DebugFacade.getResponsiveMode(instanceId), request,
                            Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.FEATURES.action())) {
                    sendRespone(DebugFacade.getFeatureStatuses(instanceId), request,
                            Response.ContentType.APPLICATION_JSON);
                }
            });

        });
    }

    @Override
    public void put(HttpExchange request) {

        Boolean doSuspend = queryParameters.get(DO_SUSPEND) == null ? false :
                Boolean.parseBoolean(queryParameters.get(DO_SUSPEND));
        String section = queryParameters.get(SECTION);
        String itemName = queryParameters.get(ITEM_NAME);


        getPathParameter(4).ifPresent(instanceId -> {
            getPathParameter(5).ifPresent(action -> {
                if (action.equals(Action.BRANCH.action())) {
                    getPathParameter(6).ifPresent(branchName -> {
                        sendRespone(DebugFacade.putProductBranch(instanceId, branchName), request,
                                Response.ContentType.TEXT_PLAIN);
                    });
                } else if (action.equals(Action.PERCENTAGE.action())) {
                    sendRespone(DebugFacade.
                                    setDevicePercentageRange4Feature(instanceId, section, itemName, body), request,
                            Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.FEATURES.action())) {
                    getPathParameter(6).ifPresent(featureName -> {
                        sendRespone(DebugFacade.
                                        setDevicePercentageRange4Feature(instanceId, "", featureName, body), request,
                                Response.ContentType.TEXT_PLAIN);
                    });
                } else if (action.equals(Action.RESPONSIVE_MODE.action())) {
                    sendRespone(DebugFacade.
                                    setResponsiveMode(instanceId, body), request,
                            Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.STREAMS.action())) {
                    getPathParameter(6).ifPresent(streamId -> {

                        if (getPathParameter(8).isPresent()) {
                            getPathParameter(8).ifPresent(streamAction -> {
                                sendRespone(DebugFacade.
                                                runActionOnStream(instanceId, streamId, streamAction, doSuspend), request,
                                        Response.ContentType.TEXT_PLAIN);
                            });
                        } else {
                            sendRespone(DebugFacade.
                                            resetStreams(instanceId, streamId,
                                                    new Gson().fromJson(body, List.class)), request,
                                    Response.ContentType.TEXT_PLAIN);
                        }

                    });
                } else if (action.equals(Action.BRANCHS.action())) {
                    getPathParameter(6).ifPresent(branchName -> {
                        sendRespone(DebugFacade.
                                        putProductBranch(instanceId, branchName), request,
                                Response.ContentType.TEXT_PLAIN);
                    });
                }
            });
        });
    }

    enum Action {
        FEATURES("features"),
        BRANCH("branch"),
        BRANCHS("branches"),
        EXPERIMENT("experiment"),
        EXPERIMENTS("experiments"),
        STREAMS("streams"),
        STREAM("stream"),
        PERCENTAGE("percentage"),
        RESPONSIVE_MODE("responsive-mode"),
        RESET("reset"),
        ACTIONS("actions"),
        CLEAR("clearTrace"),
        SUSPEND("suspend");


        private String action;

        Action(String action) {
            this.action = action;
        }

        public String action() {
            return action;
        }
    }
}
