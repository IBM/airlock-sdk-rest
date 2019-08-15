package com.ibm.app;

import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.airlock.sdk.cache.pref.FilePreferencesFactory;
import com.ibm.app.services.DebugAirlockProduct;
import com.ibm.app.util.AnnotationUtil;
import com.ibm.app.util.DynamicApi;
import io.swagger.annotations.Api;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;


public class EmptyServletContextListener implements ServletContextListener {

    private final Logger logger = Logger.getLogger(EmptyServletContextListener.class.toString());

    public static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null) {
            value = System.getProperty(key);
            if (value == null) {
                value = defaultValue;
            }
        }
        return value;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Empty Listener Rest API service has been started successfully");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
