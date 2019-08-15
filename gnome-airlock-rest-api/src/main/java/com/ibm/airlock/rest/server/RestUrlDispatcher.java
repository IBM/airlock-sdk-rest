package com.ibm.airlock.rest.server;

import com.ibm.airlock.rest.server.handlers.*;
import com.ibm.airlock.rest.util.StatisticsManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class RestUrlDispatcher implements HttpHandler {

    private static final Logger logger = Logger.getLogger(RestUrlDispatcher.class.toString());
    public static final int CORS_FILTER_MAX_AGE_IN_SECS = 12 * 60 * 60;

    public static final String CONTEXT_PREFIX = "\\/airlock\\/api";

    private final static List<RequestMetaData> handlers = new ArrayList<>();
    private static Handler productHandler = new ProductHandler();
    private static Handler productActionHandler = new ProductActionsHandler();
    private static Handler contextHandler = new ContextHandler();
    private static Handler userGroupsHandler = new UserGroupsHandler();
    private static Handler featuresHandler = new FeatureHandler();
    private static Handler stringsHandler = new StringsHandler();
    private static Handler streamsHandler = new StreamsHandler();
    private static Handler debugHandler = new DebugHandler();
    private static Handler analyticsHandler = new AnalyticsHandler();
    private static Handler aboutHandler = new AboutHandler();
    private static Handler performanceHandler = new PerformanceHandler();
    private static Handler serverLifecycleHandler = new ServerLifecycleHandler();
    private static Handler subscriptionsHandler = new SubscriptionsHandler();


    public RestUrlDispatcher() {

        // about
        handlers.add(new RequestMetaData("\\/about(\\/)?", aboutHandler));

        // performance
        handlers.add(new RequestMetaData("\\/performance(\\/)?", performanceHandler));


        // Server Lifecycle
        handlers.add(new RequestMetaData("\\/appLifecycle\\/preStop(\\/)?", serverLifecycleHandler));
        handlers.add(new RequestMetaData("\\/appLifecycle\\/readiness(\\/)?", serverLifecycleHandler));
        handlers.add(new RequestMetaData("\\/appLifecycle\\/postStart(\\/)?", serverLifecycleHandler));
        handlers.add(new RequestMetaData("\\/appLifecycle\\/liveness(\\/)?", serverLifecycleHandler));


        // analytics
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/analytics(\\/)?", analyticsHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/analytics\\/features\\/([^/]+)(\\/)?", analyticsHandler));


        //  debug
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/cache(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/branches\\/([^/]+)(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/branches(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/branch(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/features(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/percentage(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/experiment(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/experiments(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/streams(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/streams\\/([^/]+)\\/clearTrace(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/streams\\/([^/]+)\\/reset(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/streams\\/([^/]+)\\/actions\\/([^/]+)(\\/)?", debugHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/responsive-mode(\\/)?", debugHandler));

        // product
        handlers.add(new RequestMetaData("\\/products\\/init(\\/)?", productHandler));
        handlers.add(new RequestMetaData("\\/products\\/context(\\/)?", contextHandler));
        handlers.add(new RequestMetaData("\\/products\\/pullAll(\\/)?", productActionHandler));
        handlers.add(new RequestMetaData("\\/products(\\/)?", productHandler));

        // streams
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/streams/results(\\/)?", streamsHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/streams/run(\\/)?", streamsHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/streams/addEvents(\\/)?", streamsHandler));

        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/context(\\/)?", contextHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/context\\/current(\\/)?", contextHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/context\\/last-calculated(\\/)?", contextHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/pull(\\/)?", productActionHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/locale(\\/)?", productActionHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/features\\/([^/]+)(\\/)?", featuresHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/features\\/([^/]+)\\/children(\\/)?", featuresHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/calculate(\\/)?", productActionHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/strings\\/([^/]+)(\\/)?", stringsHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/strings(\\/)?", stringsHandler));


        // notifications
        handlers.add(new RequestMetaData("\\/notify(\\/)?", subscriptionsHandler));
        handlers.add(new RequestMetaData("\\/notify\\/([^/]+)(\\/)?", subscriptionsHandler));


        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/sync(\\/)?", productActionHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/usergroups(\\/)?", userGroupsHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)\\/usergroups/all(\\/)?", userGroupsHandler));
        handlers.add(new RequestMetaData("\\/products\\/([^/]+)(\\/)?", productHandler));

    }

    @Override
    public void handle(final HttpExchange httpExchange) {
        logger.fine("Request:" + httpExchange.getRequestURI() + " [" + httpExchange.getRequestMethod() + "]");
        addCORSsHeaders(httpExchange);
        //find handler which matches the given url path
        Optional<RequestMetaData> handler = handlers.stream().filter((s) -> s.matchHandler(httpExchange.getRequestURI().
                getPath())).findFirst();
        if (handler.isPresent()) {
            String handlerName = handler.get().getUrlPattern();
            long startProcessingTime = System.currentTimeMillis();
            try {
                handler.get().getHandler().handle(httpExchange);
            } catch (Throwable e) {
                logger.severe(e.getMessage());
                BaseHandler.sentServerError(httpExchange, 505, e.getMessage());
            }
            StatisticsManager.updateHandlerRequestProcessingTime(handlerName, (System.currentTimeMillis() - startProcessingTime));
            StatisticsManager.incrementHandlerRequestCounter(handlerName);
        } else {
            BaseHandler.sentServerError(httpExchange, 404, "Bad request: there is not rest service matching given request");
        }
    }

    public static void setProductHandler(Handler productHandler) {
        RestUrlDispatcher.productHandler = productHandler;
    }

    public static void setProductActionHandler(Handler productActionHandler) {
        RestUrlDispatcher.productActionHandler = productActionHandler;
    }

    private void addCORSsHeaders(final HttpExchange httpExchange) {

        if (httpExchange.getResponseHeaders() != null) {
            httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            httpExchange.getResponseHeaders().set("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            httpExchange.getResponseHeaders().set("Access-Control-Allow-Credentials", "true");
            httpExchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            httpExchange.getResponseHeaders().set("Access-Control-Max-Age", CORS_FILTER_MAX_AGE_IN_SECS + "");
        }
    }
}
