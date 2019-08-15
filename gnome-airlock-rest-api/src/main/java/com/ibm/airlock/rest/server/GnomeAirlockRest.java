package com.ibm.airlock.rest.server;

import com.ibm.airlock.rest.common.InstancesRetentionService;
import com.ibm.airlock.rest.server.handlers.DebugConsoleHandler;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.airlock.sdk.config.ConfigurationManager;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Denis
 */
public class GnomeAirlockRest {

    private static Logger logger = null;
    private static SimpleHttpServer simpleHttpServer;
    private static String LOG_LEVEL_ENV_NAME = "VERBOSE";

    static {
        Level level = Level.INFO;
        if (System.getenv(LOG_LEVEL_ENV_NAME) != null && System.getenv(LOG_LEVEL_ENV_NAME).length() > 0) {
            if (System.getenv(LOG_LEVEL_ENV_NAME).trim().equals("true"))
                level = Level.FINE;
        }
        InputStream inputStream = GnomeAirlockRest.class.getResourceAsStream("/commons-logging.properties");
        if (null != inputStream) {
            try {
                LogManager.getLogManager().readConfiguration(inputStream);
            } catch (IOException e) {
                Logger.getGlobal().log(Level.SEVERE, "init logging system", e);
            }

            Level final_level = level;
            Arrays.stream(LogManager.getLogManager().getLogger("").getHandlers()).forEach(h -> h.setLevel(final_level));

            logger = Logger.getLogger(GnomeAirlockRest.class.getCanonicalName());
            logger.log(Level.INFO, "Log initialized in level " + level.getName());
        }
    }


    private static final String CONTEXT = "/airlock/api/";
    private static final int PORT = 8080;
    private static final int THREADS = 5;

    public static void start() {
        if (simpleHttpServer == null) {
            main(new String[]{});
        } else if (!simpleHttpServer.isRunning()) {
            main(new String[]{});
        }
    }

    public static void stop() {
        if (simpleHttpServer != null) {
            simpleHttpServer.stop();
        }
    }

    public static boolean isRunning() {
        if (simpleHttpServer != null) {
            return simpleHttpServer.isRunning();
        }
        return false;
    }


    public static void main(String[] args) {

        if (simpleHttpServer != null && simpleHttpServer.isRunning()) {
            return;
        }

        // Create http server
        simpleHttpServer = new SimpleHttpServer(PORT, CONTEXT,
                new RestUrlDispatcher());

        // Start the server
        simpleHttpServer.start();
        logger.log(Level.INFO, "Server is started and listening on port " + PORT);
    }

    private static class SimpleHttpServer {

        private HttpServer httpServer;
        private boolean isRunning;

        /**
         * Instantiates a new simple http server.
         *
         * @param port    the port
         * @param context the context
         * @param handler the handler
         */
        public SimpleHttpServer(int port, String context, HttpHandler handler) {
            try {
                //Create HttpServer which is listening on the given port
                httpServer = HttpServer.create(new InetSocketAddress(port), 0);
                AirlockMultiProductsManager.getInstance().restoreProductsState();
                InstancesRetentionService.getInstance().startCleanProductsService();
                logger.log(Level.INFO, AirlockMultiProductsManager.getInstance().getAllProducts().size() +
                        " airlock product instances have been restored.");

                initDebugConsoleHandler(httpServer);

                //Create a new context for the given context and handler
                httpServer.createContext(context + "products", handler);
                httpServer.createContext(context + "about", handler);
                httpServer.createContext(context + "appLifecycle", handler);
                httpServer.createContext(context + "performance", handler);
                httpServer.createContext(context + "notify", handler);

                //Create a default executor
                httpServer.setExecutor(Executors.newFixedThreadPool(THREADS));
                logger.log(Level.INFO, "Server started with [" + THREADS + "] request executor");
            } catch (IOException e) {
                Logger.getGlobal().log(Level.SEVERE, "Server start failed", e);
            }
        }

        private void initDebugConsoleHandler(HttpServer httpServer) {
            try {
                HttpHandler debugConsole = new DebugConsoleHandler(
                        new File(ConfigurationManager.getCacheVolume() == null ? "" : ConfigurationManager.getCacheVolume()).getAbsolutePath() +
                                File.separator + "debug-console", new File("").getAbsolutePath() +
                        File.separator + "debug-console.zip");

                httpServer.createContext("/debug-console", debugConsole);
                httpServer.createContext("/", debugConsole);
            } catch (Exception e) {
                Logger.getGlobal().log(Level.WARNING, "Debug Console handler initialization failed.", e);
            }
        }


        public boolean isRunning() {
            return isRunning;
        }

        public void stop() {
            httpServer.stop(0);
            isRunning = false;
        }

        /**
         * Start.
         */
        public void start() {
            this.httpServer.start();
            isRunning = true;
        }
    }
}


