package com.ibm.app.storage;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.json.JSONException;
import org.json.JSONObject;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.logging.Logger;

public class AzureStorageDAO implements CloudStorageDAO {

    public static final Logger logger = Logger.getLogger(AzureStorageDAO.class.getName());

    public static String AZURE_STORAGE_ACCOUNT_NAME = "AZURE_STORAGE_ACCOUNT_NAME";
    public static String AZURE_STORAGE_ACCOUNT_KEY = "AZURE_STORAGE_ACCOUNT_KEY";
    public static String AZURE_INTERNAL_CONTAINER_NAME = "AZURE_INTERNAL_CONTAINER_NAME";


    public static int S3_ACTION_RETRY_NUM = 3;
    public static int RETRY_INTERVAL_MS = 1000; //sleep 1 second between IO actions trials;

    public static String SEPARATOR = "/";

    private String azureStorageAccountName;
    private String azureStorageAccountKey;
    private String azureInternalContainerName;
    private CloudBlobContainer internalContainer;


    private static AzureStorageDAO azureStorageDAO;

    public static synchronized AzureStorageDAO getInstance() throws ConfigurationException {
        if (azureStorageDAO == null) {
            azureStorageDAO = new AzureStorageDAO();
        }
        return azureStorageDAO;
    }

    private AzureStorageDAO() throws ConfigurationException {

        this.azureStorageAccountName = "airlockstorage";//System.getenv(AZURE_STORAGE_ACCOUNT_NAME);
        this.azureStorageAccountKey = "HOEydpmfEcjBQfZPPzCIEhKzmRSkTc4vK5vByjVVi30Dfmv4iG2j6H7rMAkqfFbnrKdHeoEszfHoyHAjvzcwjg==";//System.getenv(AZURE_STORAGE_ACCOUNT_KEY);
        this.azureInternalContainerName = "airlockdevinternal";//System.getenv(AZURE_INTERNAL_CONTAINER_NAME);

        if (this.azureStorageAccountName == null
                || this.azureStorageAccountKey == null || this.azureInternalContainerName == null) {
            throw new ConfigurationException("Azure storage access configuration not found");
        }

        String storageConnectionString =
                "DefaultEndpointsProtocol=https;" +
                        "AccountName=" + azureStorageAccountName + ";" +
                        "AccountKey=" + azureStorageAccountKey;


        CloudStorageAccount storageAccount = null;
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            this.internalContainer = blobClient.getContainerReference(azureInternalContainerName);
        } catch (URISyntaxException | InvalidKeyException | StorageException e) {
            logger.severe(e.getMessage());
            throw new ConfigurationException(e.getMessage());
        }
    }


    @Override
    public JSONObject readDataAsJSON(String path) throws IOException, JSONException {
        return new JSONObject(readDataAsString(path));
    }

    @Override
    public String readDataAsString(String path) throws IOException {
        try {
            String jsonData = doReadAzureObject(path, S3_ACTION_RETRY_NUM);
            return jsonData;
        } catch (IOException ioe) {
            String err = "Failed reading JSON data from " + path + ": " + ioe.getMessage();
            throw new IOException(err);
        } catch (JSONException je) {
            String err = "Failed reading JSON data from " + path + ": " + je.getMessage();
            throw new JSONException(err);
        }
    }

    //try to read 3 times
    private String doReadAzureObject(String path, int numberOfTrials) throws IOException {
        String lastErrStr = "";
        for (int i = 0; i < numberOfTrials; i++) {
            try {
                CloudBlockBlob blob = internalContainer.getBlockBlobReference(path);
                return blob.downloadText();
            } catch (StorageException e) {
                lastErrStr = e.getMessage();
                logger.warning("Failed reading data from '" + path + "', trial number " + (i + 1) + ": " + lastErrStr);
            } catch (URISyntaxException e) {
                lastErrStr = e.getMessage();
                logger.warning("Failed reading data from '" + path + "', trial number " + (i + 1) + ": " + lastErrStr);
            }

            try {
                if (i < numberOfTrials - 1) //sleep between trials (but not after the last)
                    Thread.sleep(RETRY_INTERVAL_MS);
            } catch (InterruptedException e) {
                //do nothing
            }
        }
        String err = "Failed reading data from '" + path + "' : " + lastErrStr;
        logger.severe(err);
        throw new IOException(err);
    }

}
