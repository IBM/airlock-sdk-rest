package com.ibm.app.services;

import com.ibm.airlock.common.net.AirlockDAO;
import com.ibm.airlock.rest.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;



public class DebugAirlockProductNegativeTest extends BaseServiceTest {

    private final static String strJson = "{" +
            "\"defaultLanguage\": \"en\"," +
            "\"devS3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\"," +
            "\"productId\": \"67ac3306-0f75-4d9a-8434-f72771bcb670\"," +
            "\"productName\": \"TestJavaSDK2\"," +
            "\"root\": {" +
            "\"features\": [" +
            "{" +
            "\"defaultConfiguration\": null," +
            "\"defaultIfAirlockSystemIsDown\": false," +
            "\"features\": [" +
            "]," +
            "\"name\": \"featureBasedOnProductContext\"," +
            "\"namespace\": \"f\"," +
            "\"noCachedResults\": false," +
            "\"type\": \"FEATURE\"," +
            "\"uniqueId\": \"2a14394f-c912-4a17-94c4-86517ff40cff\"" +
            "}," +
            "{" +
            "\"defaultConfiguration\": null," +
            "\"defaultIfAirlockSystemIsDown\": false," +
            "\"features\": [" +
            "]," +
            "\"name\": \"featueOnBasedContexed\"," +
            "\"namespace\": \"feature\"," +
            "\"noCachedResults\": false," +
            "\"type\": \"FEATURE\"," +
            "\"uniqueId\": \"7b075061-5c4d-4f64-a148-280f37af4f2f\"" +
            "}" +
            "]," +
            "\"type\": \"ROOT\"," +
            "\"uniqueId\": \"011f482f-dfbe-4fc2-9037-f0629319be33\"" +
            "}," +
            "\"s3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\"," +
            "\"seasonId\": \"e55c9714-c8df-470c-a148-5528764d3c7e\"," +
            "\"supportedLanguages\": [" +
            "\"en\"" +
            "]," +
            "\"version\": \"V2.5\"" +
            "}";


    public DebugAirlockProductNegativeTest() {
        defaults = strJson;
        productId = getProductId();
    }

    @Before
    public void pullAndCalculate() {
        try {
            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"Adina\"]"));
            Assert.assertEquals(200, response.getStatus());
            response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("productId", productId).queryParam("locale", "en_US").request().put(Entity.entity(productId, MediaType.TEXT_PLAIN_TYPE));
            Assert.assertEquals(200, response.getStatus());
            response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).queryParam("productId", productId).
                    request().put(Entity.json("{\"device\": {\"screenWidth\": 8 }}"));
            Assert.assertEquals(200, response.getStatus());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getExperimentsListInfoTest() {
        try {
            ExperimentList experimentsList = client.target(serverUrl + "/products/" + productInstanceId + "/experiments").request().get(ExperimentList.class);
            Experiment[] experiments = experimentsList.getExperiments();
            Assert.assertTrue(experiments.length ==  0);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getExperimentInfoTest() {
        try {
            Experiment experiment = client.target(serverUrl + "/products/" + productInstanceId + "/experiment").request().get(Experiment.class);
            Assert.assertTrue(experiment.getName() == null);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}