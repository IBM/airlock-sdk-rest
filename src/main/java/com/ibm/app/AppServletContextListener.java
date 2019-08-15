package com.ibm.app;

import com.ibm.airlock.rest.common.InstancesRetentionService;
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


public class AppServletContextListener implements ServletContextListener {

    private final Logger logger = Logger.getLogger(AppServletContextListener.class.toString());

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
        ServletContext context = sce.getServletContext();
        setDebugAPIVisibility();
        InstancesRetentionService.getInstance().startCleanProductsService();
        AirlockMultiProductsManager.getInstance().restoreProductsState();
        context.setAttribute(Constants.AIRLOCK_CACHE_LOCATION, FilePreferencesFactory.getAirlockCacheDirectory());

        logger.info("Airlock Rest API service has been started successfully");
    }

    // read the Constants.FOLDER_PATH_ENV_PROPERTY if the property is true
    // the debug API will be visible from public swagger, by default it's hidden.
    private void setDebugAPIVisibility() {
        Boolean debugApiIsVisible = Boolean.parseBoolean(getEnv(Constants.DEBUG_API_IS_VISIBLE, "false"));
        DynamicApi dynamicApi = new DynamicApi(!debugApiIsVisible, DebugAirlockProduct.class.getAnnotation(Api.class));
        AnnotationUtil.alterAnnotationOn(DebugAirlockProduct.class, Api.class, dynamicApi);
        logger.info("Airlock Rest API service has been started with debug api "+(debugApiIsVisible?"":"not")+" visible");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
