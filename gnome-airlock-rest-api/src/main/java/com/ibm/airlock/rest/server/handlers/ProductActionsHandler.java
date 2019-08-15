package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.facades.ProductActionsFacade;
import com.sun.net.httpserver.HttpExchange;

/**
 * @author Denis Voloshin
 */

public class ProductActionsHandler extends BaseHandler {

    private static final String SYNC = "sync";
    private static final String LOCALE = "locale";

    @Override
    public void put(HttpExchange request) {

        final Boolean sync = Boolean.parseBoolean(queryParameters.get(SYNC));
        final String locale = queryParameters.get(LOCALE);


        getPathParameter(4).ifPresent(instanceId -> {
            getPathParameter(5).ifPresent(action -> {
                if (action.equals(Action.PULL.action())) {
                    sendRespone(ProductActionsFacade.pull(instanceId, locale),
                            request, Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.CALC.action())) {
                    sendRespone(ProductActionsFacade.calculate(instanceId, sync),
                            request, Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.SYNC.action())) {
                    sendRespone(ProductActionsFacade.sync(instanceId),
                            request, Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.PULL_ALL.action())) {
                    sendRespone(ProductActionsFacade.pullAll(),
                            request, Response.ContentType.TEXT_PLAIN);
                }
            });
        });

        getPathParameter(4).ifPresent(action -> {
            if (action.equals(Action.PULL_ALL.action())) {
                sendRespone(ProductActionsFacade.pullAll(),
                        request, Response.ContentType.TEXT_PLAIN);
            }
        });
    }

    @Override
    public void get(HttpExchange request) {
        getPathParameter(4).ifPresent(instanceId -> {
            getPathParameter(5).ifPresent(action -> {
                if (action.equals(Action.PULL.action())) {
                    sendRespone(ProductActionsFacade.getLastPullTime(instanceId),
                            request, Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.CALC.action())) {
                    sendRespone(ProductActionsFacade.getLastCalculateTime(instanceId),
                            request, Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.SYNC.action())) {
                    sendRespone(ProductActionsFacade.getLastSyncTime(instanceId),
                            request, Response.ContentType.TEXT_PLAIN);
                } else if (action.equals(Action.LOCAL.action())) {
                    sendRespone(ProductActionsFacade.getLastPullLocale(instanceId),
                            request, Response.ContentType.TEXT_PLAIN);
                }
            });
        });
    }


    enum Action {
        CALC("calculate"),
        PULL("pull"),
        SYNC("sync"),
        PULL_ALL("pullAll"),
        LOCAL("locale");

        private String action;

        Action(String action) {
            this.action = action;
        }

        public String action() {
            return action;
        }
    }
}
