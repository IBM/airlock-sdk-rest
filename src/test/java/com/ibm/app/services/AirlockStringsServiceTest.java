package com.ibm.app.services;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class AirlockStringsServiceTest extends BaseServiceTest {

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


    public AirlockStringsServiceTest() {
        defaults = strJson;
        productId = getProductId();
    }

    @Override
    public void resetAllProducts() {
        super.resetAllProducts();
        Response response = client.target(serverUrl + "/products/context").queryParam("clearPreviousContext", true).request().put(Entity.entity("{}", MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());

    }

    @Before
    public void pull() {
        try {
            Response pullResponse = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE), Response.class);
            Assert.assertEquals(pullResponse.getStatus(), 200);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getStringByKey() {

        String stringId = "strKey";
        try {
            Response stringValue = client.target(serverUrl + "/products/" + productInstanceId + "/strings/" + stringId).request().put(Entity.json("[]"));
            Assert.assertEquals("strValue", (stringValue.readEntity(String.class)));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getNonExistingStringByKey() {
        String stringId = "kuku.notExsist";
        try {
            Response getStringResponse = client.target(serverUrl + "/products/" + productInstanceId + "/strings/" + stringId).request().put(Entity.json("[]"));
            Assert.assertEquals(getStringResponse.getStatus(), 404);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getStringByKeyWithArguments() {
        String stringId = "strEithParams";
        try {
            Response stringValue = client.target(serverUrl + "/products/" + productInstanceId + "/strings/" + stringId).request().put(Entity.json("[\"2\"]"));
            Assert.assertEquals("2 PARAM", (stringValue.readEntity(String.class)));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getStringByKeyWithInvalidBody() {
        String stringId = "General.multiDaysUpper";
        try {
            Response getStringResponse = client.target(serverUrl + "/products/" + productInstanceId + "/strings/" + stringId).request().put(Entity.entity("",MediaType.TEXT_PLAIN_TYPE));
            Assert.assertEquals( 415, getStringResponse.getStatus());

            getStringResponse = client.target(serverUrl + "/products/" + productInstanceId + "/strings/" + stringId).request().put(Entity.json("{}"));
            Assert.assertEquals( 400, getStringResponse.getStatus());

            getStringResponse = client.target(serverUrl + "/products/" + productInstanceId + "/strings/" + stringId).request().put(Entity.json("[]"));
            Assert.assertEquals( 404, getStringResponse.getStatus());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
