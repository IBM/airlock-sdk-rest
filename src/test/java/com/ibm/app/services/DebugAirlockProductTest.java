package com.ibm.app.services;

import com.ibm.airlock.common.net.AirlockDAO;
import com.ibm.airlock.rest.model.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class DebugAirlockProductTest extends BaseServiceTest {

    private final static String strJson = "{\n" +
            "	\"defaultLanguage\": \"en\",\n" +
            "	\"devS3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\",\n" +
            "	\"productId\": \"cdd52d55-df5d-4375-ac41-1086e4f1c7a3\",\n" +
            "	\"productName\": \"TestJavaSDK\",\n" +
            "	\"root\": {\n" +
            "		\"features\": [\n" +
            "			{\n" +
            "				\"defaultConfiguration\": null,\n" +
            "				\"defaultIfAirlockSystemIsDown\": false,\n" +
            "				\"features\": [\n" +
            "				],\n" +
            "				\"name\": \"sdkFeature\",\n" +
            "				\"namespace\": \"sdk\",\n" +
            "				\"noCachedResults\": false,\n" +
            "				\"type\": \"FEATURE\",\n" +
            "				\"uniqueId\": \"a6b7d521-c58f-44dc-9464-7901c199b1ec\"\n" +
            "			}\n" +
            "		],\n" +
            "		\"type\": \"ROOT\",\n" +
            "		\"uniqueId\": \"9187e207-b9a4-4b26-a7f4-54c90f605992\"\n" +
            "	},\n" +
            "	\"s3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\",\n" +
            "	\"seasonId\": \"fe26411e-fd45-4fc3-aef0-63dc154bc009\",\n" +
            "	\"supportedLanguages\": [\n" +
            "		\"en\"\n" +
            "	],\n" +
            "	\"version\": \"V2.5\"\n" +
            "}";


    public DebugAirlockProductTest() {
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
    public void getFeatureStatusesTest() {
        try {
            List data = client.target(serverUrl + "/products/" + productInstanceId + "/features").request().get(List.class);
            data.size();
            for (Object map: data){
                Map innerMap = (Map) ((Map)map).get("debugInfo");
                if (innerMap != null) {
                    String trace = (String) innerMap.get("trace");
                    Assert.assertTrue(trace != null && !trace.isEmpty());
                }
            }

            data = client.target(serverUrl + "/products/" + productInstanceId + "/features").request().get(List.class);
            for (Object map: data){
                Map innerMap = (Map) ((Map)map).get("debugInfo");
                if (innerMap != null) {
                    String trace = (String) innerMap.get("trace");
                    Assert.assertTrue(trace != null && !trace.isEmpty());
                }
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void getFeaturePercentageTest() {
        try {
            Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/sdk.featurewWith50Percentage").request().get(Feature.class);
            Assert.assertNotNull(feature);
            Assert.assertEquals(45.0, feature.getDebugInfo().getPercentage(), 0);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }



    @Test
    public void getExperimentsListInfoTest() {
        try {
            ExperimentList experimentsList = client.target(serverUrl + "/products/" + productInstanceId + "/experiments").request().get(ExperimentList.class);
            Experiment[] experiments = experimentsList.getExperiments();
            Assert.assertTrue(experiments.length > 0);
            Experiment exp = experiments[0];
            Assert.assertTrue(exp.getName().equals("experiment1"));
            Assert.assertTrue(exp.isON());
            Assert.assertTrue(exp.getRolloutPercentage() > 90);
            Assert.assertTrue(exp.getAppVersions().startsWith("8"));
            Assert.assertTrue(exp.getTraceInfo().startsWith("Configurations"));

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getExperimentInfoTest() {
        try {
            Experiment experiment = client.target(serverUrl + "/products/" + productInstanceId + "/experiment").request().get(Experiment.class);
            Assert.assertTrue(experiment.getName().equals("experiment1"));
            Assert.assertTrue(experiment.isON());
            Assert.assertTrue(experiment.getRolloutPercentage() > 90);
            Assert.assertTrue(experiment.getAppVersions().startsWith("8"));
            Assert.assertTrue(experiment.getTraceInfo().startsWith("Configurations"));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void getEmptyExperimentInfoTest() {
        String default1 = "{\n" +
                "\t\"defaultLanguage\": \"en\",\n" +
                "\t\"devS3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\",\n" +
                "\t\"productId\": \"2c7b6ad4-11d3-4d84-a4dc-245a14d22919\",\n" +
                "\t\"productName\": \"Andrei2\",\n" +
                "\t\"root\": {\n" +
                "\t\t\"features\": [\n" +
                "\t\t],\n" +
                "\t\t\"type\": \"ROOT\",\n" +
                "\t\t\"uniqueId\": \"3a0cf91d-5141-4808-8df2-da692372e50c\"\n" +
                "\t},\n" +
                "\t\"s3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\",\n" +
                "\t\"seasonId\": \"e8c3907a-213f-49fd-ba0c-ee174b503e35\",\n" +
                "\t\"supportedLanguages\": [\n" +
                "\t\t\"en\"\n" +
                "\t],\n" +
                "\t\"version\": \"V2.5\"\n" +
                "}";
        try {
            Product newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", "7.0").queryParam("encryptionKey", "")
                    .request().post(Entity.json(default1), Product.class);
            Assert.assertNotNull(newProduct);
            Assert.assertNotNull(newProduct.getProductId(),"2c7b6ad4-11d3-4d84-a4dc-245a14d22919");
            String emptyProductInstanceId = newProduct.getInstanceId();

//            Experiment experiment = client.target(serverUrl + "/products/" + emptyProductInstanceId + "/experiment").request().get(Experiment.class);
            Response response = client.target(serverUrl + "/products/" + emptyProductInstanceId + "/experiment").request().get();
            String exp = response.readEntity(String.class);
            Assert.assertEquals("{}", exp);

            response = client.target(serverUrl + "/products/" + "2c7b6ad4-11d3-4d84-a4dc-245a14d22000" + "/experiment").request().get();
            exp = response.readEntity(String.class);


            response = client.target(serverUrl + "/products/" + emptyProductInstanceId + "/experiments").request().get();
            exp = response.readEntity(String.class);
            Assert.assertEquals("{}", exp);

            response = client.target(serverUrl + "/products/" + "2c7b6ad4-11d3-4d84-a4dc-245a14d22000" + "/experiments").request().get();
            exp = response.readEntity(String.class);

            Assert.assertEquals("Product not initialized", exp);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void getEmptyStreamsTest() {
        String default1 = "{\n" +
                "\t\"defaultLanguage\": \"en\",\n" +
                "\t\"devS3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\",\n" +
                "\t\"productId\": \"2c7b6ad4-11d3-4d84-a4dc-245a14d22919\",\n" +
                "\t\"productName\": \"Andrei2\",\n" +
                "\t\"root\": {\n" +
                "\t\t\"features\": [\n" +
                "\t\t],\n" +
                "\t\t\"type\": \"ROOT\",\n" +
                "\t\t\"uniqueId\": \"3a0cf91d-5141-4808-8df2-da692372e50c\"\n" +
                "\t},\n" +
                "\t\"s3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\",\n" +
                "\t\"seasonId\": \"e8c3907a-213f-49fd-ba0c-ee174b503e35\",\n" +
                "\t\"supportedLanguages\": [\n" +
                "\t\t\"en\"\n" +
                "\t],\n" +
                "\t\"version\": \"V2.5\"\n" +
                "}";
        try {
            Product newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", "7.0").queryParam("encryptionKey", "")
                    .request().post(Entity.json(default1), Product.class);
            Assert.assertNotNull(newProduct);
            Assert.assertNotNull(newProduct.getProductId(),"2c7b6ad4-11d3-4d84-a4dc-245a14d22919");
            String emptyProductInstanceId = newProduct.getInstanceId();

            Response response = client.target(serverUrl + "/products/" + emptyProductInstanceId + "/streams").request().get();
            Assert.assertTrue(response.getStatus() == 200);
            List<Stream> streams = response.readEntity(new GenericType<List<Stream>>() {
            });
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void getStreamsTest() {
        try {
            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/streams").request().get();
            Assert.assertTrue(response.getStatus() == 200);
            List<Stream> streams = response.readEntity(new GenericType<List<Stream>>() {
            });
            Assert.assertTrue(streams != null);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void setDeviceInFeaturePercangeRange() {
        try {

            // stream
            setDeviceInEntityPercangeRangeByName("streams", "testStreamWithPercentage");

            // feature
            setDeviceInEntityPercangeRangeByName("features","sdk.sdkFeature");

            // experiment
            setDeviceInEntityPercangeRangeByName("experiments","experiments.experiment1");

            // variant
            setDeviceInEntityPercangeRangeByName("experiments","experiment1.variant1");

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    private void setDeviceInEntityPercangeRangeByName(String section, String name) {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section",section).queryParam("itemName", name).request().put(Entity.entity("true", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertTrue(response.getStatus() == 200);

        Boolean inRange = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section",section).queryParam("itemName", name).request().get(Boolean.class);
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(inRange);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section",section).queryParam("itemName", name).request().put(Entity.entity("false", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertTrue(response.getStatus() == 200);


        inRange = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section",section).queryParam("itemName", name).request().get(Boolean.class);
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(!inRange);
    }

    @Test
    public void responsiveModeTest() {
        try {
            String mode = client.target(serverUrl + "/products/" + productInstanceId + "/responsive-mode").request().get(String.class);
            Assert.assertTrue(mode != null);
            Assert.assertEquals(mode, AirlockDAO.DataProviderType.CACHED_MODE.name());

            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/responsive-mode").request().put(Entity.entity(AirlockDAO.DataProviderType.DIRECT_MODE.name(), MediaType.TEXT_PLAIN_TYPE));
            Assert.assertTrue(response.getStatus() == 200);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


//    @Test
    public void clearCacheTest() {
        try {
            String cacheResponse = client.target(serverUrl + "/products/" + productInstanceId + "/cache").request().delete(String.class);
            Assert.assertTrue(cacheResponse != null);
            Assert.assertEquals(cacheResponse, "");



            Long lastPullTimeAfterPull = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().get(Long.class);
            Assert.assertNotNull(lastPullTimeAfterPull);
            Assert.assertTrue(lastPullTimeAfterPull == 0);

            Product newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", appVersion).queryParam("encryptionKey", encryptionKey)
                    .request().post(Entity.json(defaults), Product.class);
            Assert.assertNotNull(newProduct);
            Assert.assertNotNull(newProduct.getProductId(), productId);
            this.productInstanceId = newProduct.getInstanceId();

            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
            Assert.assertEquals(200, response.getStatus());

            lastPullTimeAfterPull = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().get(Long.class);
            Assert.assertNotNull(lastPullTimeAfterPull);
            Assert.assertTrue(lastPullTimeAfterPull > 0);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}