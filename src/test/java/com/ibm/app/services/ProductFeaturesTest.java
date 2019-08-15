package com.ibm.app.services;

import com.ibm.airlock.rest.model.Attribute;
import com.ibm.airlock.rest.model.Feature;
import com.ibm.airlock.rest.model.FeatureAnalytics;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductFeaturesTest extends BaseServiceTest {

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


    public ProductFeaturesTest() {
        defaults = strJson;
        productId = getProductId();
    }
    @Before
    public void pullAndCalculate() {
        try {
            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"Adina\"]"));
            Assert.assertEquals(200, response.getStatus());
            response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("productId",productId).queryParam("locale","en_US").request().put(Entity.entity(productId, MediaType.TEXT_PLAIN_TYPE));
            Assert.assertEquals(200, response.getStatus());
            response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync",true).queryParam("productId",productId).
                    request().put(Entity.json("{\"device\": {\"screenWidth\": 8 }}"));
            Assert.assertEquals(200, response.getStatus());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getFeatureByName() {
        try {

            Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/f.subSub").request().get(Feature.class);
            Assert.assertTrue(feature != null);
            Assert.assertTrue(feature.getName().equals("f.subSub"));
            feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/kuku").request().get(Feature.class);
            Assert.assertTrue(feature.getDebugInfo().getSource() == com.ibm.airlock.common.data.Feature.Source.MISSING);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getFeatureChildren() {
        List<Map> featureList  = client.target(serverUrl + "/products/" + productInstanceId + "/features/f.featureInMx1/children").request().get(List.class);
        Assert.assertTrue(featureList.size()>0);
        boolean expectedExists = false;
        boolean notExpectedDoesNotExist = true;
        for (Map feature: featureList){
            if (feature.get("name").equals("f.subFeature")){
                expectedExists = true;
            }
            if (feature.get("name").equals("f.kuku1")){
                notExpectedDoesNotExist = false;
            }
        }
        Assert.assertTrue(expectedExists);
        Assert.assertTrue(notExpectedDoesNotExist);
    }
}