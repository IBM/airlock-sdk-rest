package com.ibm.airlock.rest.common;

import com.ibm.airlock.rest.facades.AnalyticsFacade;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstancesRetentionService {


    private static final Logger logger = Logger.getLogger(InstancesRetentionService.class.getName());
    public static final String CLEAN_INTERVAL = "retention.clean.interval";
    public static final String MAX_ACTIVE_INSTANCES = "max.active.instances";
    public static final String MINIMUM_TIME_TO_LIVE = "minimum.time.to.live";


    private static InstancesRetentionService serviceInstance;
    private ScheduledExecutorService cleanExecutor;
    private Runnable pullTask;
    private final Hashtable<String, Long> productsMap;
    private static long cleanInterval = 10 * 60; //in milliseconds
    private static long maxActiveProductInstances = 5;
    private static long minimumTimeToLive = 60 * 24 * 60; // in milliseconds


    // set configuration.
    static {
        cleanInterval = readConfigurationValue(CLEAN_INTERVAL, cleanInterval);
        maxActiveProductInstances = readConfigurationValue(MAX_ACTIVE_INSTANCES, maxActiveProductInstances);
        minimumTimeToLive = readConfigurationValue(MINIMUM_TIME_TO_LIVE, minimumTimeToLive);
    }


    private static Long readConfigurationValue(String name, long defaultValue) {
        try {
            if (System.getenv(name) != null) {
                return Long.parseLong(System.getenv(name));
            } else if (System.getProperty(name) != null) {
                return Long.parseLong(System.getProperty(name));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return defaultValue;
    }


    /**
     * This method updates the interval which clean service will be triggered.
     * Setting a value smalled than 1 minute will reset is to its default
     *
     * @param cleanInterval
     */
    public void updateInterval(long cleanInterval) {
        if (cleanInterval < 1) {
            cleanInterval = 10;
        }
        this.cleanInterval = cleanInterval;
        cleanExecutor.scheduleAtFixedRate(pullTask, 0, cleanInterval, TimeUnit.SECONDS);
        logger.info("Instances Retention Service clean interval has been updated to " + cleanInterval + " seconds");
    }

    public void updateMaxActiveProductInstances(int maxActiveProductInstances) {
        this.maxActiveProductInstances = maxActiveProductInstances;
    }

    public void updateMinimumTimeToLive(long minimumTimeToLive) {
        this.minimumTimeToLive = minimumTimeToLive;
    }

    public void startCleanProductsService() {
        cleanExecutor = Executors.newSingleThreadScheduledExecutor();
        pullTask = () -> {
            List<String> instancesToDelete = new ArrayList<>();
            try {
                int counter = 0;
                for (String productInstanceId : productsMap.keySet()) {
                    counter++;
                    long timeSince = (System.currentTimeMillis() - productsMap.get(productInstanceId)) / 1000L;
                    if (timeSince > minimumTimeToLive) {
                        if (counter > maxActiveProductInstances) {
                            instancesToDelete.add(productInstanceId);
                        }
                    }
                }
                for (String instance : instancesToDelete) {
                    deleteProductInstance(instance);
                }
                instancesToDelete.clear();
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        };
        cleanExecutor.scheduleAtFixedRate(pullTask, 0, cleanInterval, TimeUnit.SECONDS);
        logger.info("Instances Retention Service is started with cleaning  interval of " + cleanInterval + " seconds");
    }

    public long getCleanInterval() {
        return cleanInterval;
    }

    public static synchronized InstancesRetentionService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new InstancesRetentionService();
        }
        return serviceInstance;
    }

    private InstancesRetentionService() {
        productsMap = new Hashtable<>();
    }

    public Long getProductLastUsed(String productInstanceId) {
        return productsMap.get(productInstanceId);
    }

    public void setProductLastUsed(String productInstanceId, Long productLastUsed) {
        productsMap.put(productInstanceId, productLastUsed);
    }

    private void deleteProductInstance(String productInstanceId) {
        AirlockMultiProductsManager.getInstance().removeAirlockProductManager(productInstanceId);
        productsMap.remove(productInstanceId);
        logger.info("Instances [" + productInstanceId + "] has been deleted by Instances Retention Service");
    }
}
