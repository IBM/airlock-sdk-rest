package com.ibm.app.services;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;


public class UserGroupsServiceTest extends BaseServiceTest {

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


    public UserGroupsServiceTest() {
        defaults = strJson;
        productId = getProductId();
    }

    @Test
    public void addAndRemoveUserGroupsToProduct() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"Adina\"]"));
        Assert.assertEquals(200, response.getStatus());

        response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);


        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).queryParam("productId", productId).
                request().put(Entity.json("{\"device\": {\"screenWidth\": 8 }}"));

        List data = client.target(serverUrl + "/products/" + productInstanceId + "/features").request().get(List.class);
        data.size();
        for (Object map: data){
            Map innerMap = (Map) ((Map)map).get("debugInfo");
            if (innerMap != null) {
                String trace = (String) innerMap.get("trace");
                Assert.assertTrue(trace != null && !trace.isEmpty());
            }
        }

        response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("notValidInput"));
        Assert.assertEquals(400, response.getStatus());


        response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.readEntity(String.class).contains("Adina"));
        response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"dummyToEmptyList\"]"));
        Assert.assertEquals(200, response.getStatus());


        response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[]"));
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.readEntity(String.class).equals(""));

        response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).queryParam("productId", productId).
                request().put(Entity.json("{\"device\": {\"screenWidth\": 8 }}"));

        data = client.target(serverUrl + "/products/" + productInstanceId + "/features").request().get(List.class);
        data.size();
        for (Object map: data){
            Map innerMap = (Map) ((Map)map).get("debugInfo");
            if (innerMap != null) {
                String trace = (String) innerMap.get("trace");
                Assert.assertTrue(trace != null && !trace.isEmpty());
            }
        }

    }

}
