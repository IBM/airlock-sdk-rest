package com.ibm.app.airlock;

import com.ibm.airlock.common.util.AirlockVersionComparator;
import com.ibm.app.storage.AzureStorageDAO;
import com.ibm.app.storage.CloudStorageDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;

public class AirlockDAO {

    public static final String AIRLOCK_DEFAULTS_PATH_FORMAT = "seasons/%s/%s/AirlockDefaults.json";
    public static final String AIRLOCK_ENCRYPTION_KEY = "seasons/%s/%s/AirlockEncryptionKey.json";
    private CloudStorageDAO cloudStorageDAO;

    private static AirlockDAO airlockDAO;

    public synchronized static AirlockDAO getInstance() {
        if (airlockDAO == null) {
            airlockDAO = new AirlockDAO();
        }
        return airlockDAO;
    }

    private AirlockDAO() {
        ClassLoader classLoader = CloudStorageDAO.class.getClassLoader();

        try {
            Class aClass = classLoader.loadClass("com.ibm.app.storage.AzureStorageDAOs");
            Method method = aClass.getMethod("getInstance", new Class[0]);
            cloudStorageDAO = (CloudStorageDAO) method.invoke(null, new Object[0]);

        } catch (Exception e) {
            //do nothing class not supported.
        }
    }

    public String getEncryptionKey(String productId, String appVersion) throws Exception {

        if (cloudStorageDAO == null) {
            throw new UnsupportedOperationException();
        }

        JSONObject season = getSeason(productId, appVersion);

        if (season != null) {
            String productID = season.getString("productId");
            String seasonID = season.getString("uniqueId");
            String remoteDefaultUrl = getAirlockEncryptionKeyPath(productID, seasonID);

            // check if the default file stream is empty (means that there is no default file found for the url)
            return AzureStorageDAO.getInstance().readDataAsString(remoteDefaultUrl);
        } else {
            throw new IOException("season was not determined for " + productId + "app version" + appVersion);
        }

    }


    private  JSONObject getSeason(String productId, String appVersion) throws Exception {
        JSONObject season = null;

        JSONArray seasonsArray = getSeasonsByProductId(productId);
        if (seasonsArray == null) {
            throw new IOException("No season found for productId:" + productId + " version:" + appVersion);
        }
        for (int i = 0; i < seasonsArray.length(); i++) {
            JSONObject seasonJSON = seasonsArray.getJSONObject(i);
            if (verifySeason(seasonJSON.optString("minVersion", ""), seasonJSON.optString("maxVersion", "null"), appVersion)) {
                season = seasonJSON;
            }
        }
        return season;
    }

    public String getDefaults(String productId, String appVersion) throws Exception {

        if(cloudStorageDAO==null){
            throw new UnsupportedOperationException();
        }

        JSONObject season = getSeason(productId, appVersion);

        if (season != null) {
            String productID = season.getString("productId");
            String seasonID = season.getString("uniqueId");
            String remoteDefaultUrl = getDefaultsFilePath(productID, seasonID);

            // check if the default file stream is empty (means that there is no default file found for the url)
            return AzureStorageDAO.getInstance().readDataAsString(remoteDefaultUrl);
        } else {
            throw new IOException("season was not determined for " + productId + "app version" + appVersion);
        }
    }


    private JSONArray getSeasonsByProductId(String productId) throws Exception {
        JSONObject products = new JSONObject(AzureStorageDAO.getInstance().readDataAsString("products.json"));
        JSONArray productsArray = products.getJSONArray("products");
        for (int i = 0; i < productsArray.length(); i++) {
            JSONObject product = productsArray.getJSONObject(i);
            if (product.get("uniqueId").equals(productId)) {
                return product.getJSONArray("seasons");
            }
        }
        return new JSONArray();
    }

    private static boolean verifySeason(String minVersion, String maxVersion, String productVersion) {
        AirlockVersionComparator comparator = new AirlockVersionComparator();
        if (comparator.compare(minVersion, productVersion) <= 0) {
            if (maxVersion.equalsIgnoreCase("null") ||
                    comparator.compare(maxVersion, productVersion) > 0) {
                return true;
            }
        }
        return false;
    }

    private String getAirlockEncryptionKeyPath(String productId, String seasonId) {
        return String.format(AIRLOCK_ENCRYPTION_KEY, productId, seasonId);
    }

    private String getDefaultsFilePath(String productId, String seasonId) {
        return String.format(AIRLOCK_DEFAULTS_PATH_FORMAT, productId, seasonId);
    }

    public static void main(String args[]) {
        try {
            AirlockDAO airlockDAO = AirlockDAO.getInstance();
            System.out.println(airlockDAO.getDefaults("0b0490bd-cb24-4f1e-b727-051783a295ad", "3.0.0"));
            System.out.println(airlockDAO.getEncryptionKey("0b0490bd-cb24-4f1e-b727-051783a295ad", "3.0.0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
