package com.ibm.app.services;

import com.ibm.airlock.rest.model.Stream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class DebugToolTests extends BaseServiceTest {

    private final static String strJson = "{\n" +
            "  \"devS3Path\": \"https://airlockstorage.blob.core.windows.net/dev4runtime/\",\n" +
            "  \"defaultLanguage\": \"en\",\n" +
            "  \"productId\": \"d1ac07f9-10db-4848-aafb-3366214ae794\",\n" +
            "  \"s3Path\": \"https://airlockstorage.blob.core.windows.net/dev4runtime\",\n" +
            "  \"supportedLanguages\": [\n" +
            "    \"en\"\n" +
            "  ],\n" +
            "  \"seasonId\": \"1816132f-bf8a-480c-8343-5ea1dd6fa646\",\n" +
            "  \"root\": {\n" +
            "    \"features\": [\n" +
            "      {\n" +
            "        \"features\": [\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"F11\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"5bfba857-2944-454e-a73a-798c7347306f\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"F11k\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"8bd74cbf-29ce-4146-b75e-2b941f4cf5b8\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"F11l\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"6917f90a-f753-4d1f-83bb-26d30668c167\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"f11ooo\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"44acc2d3-b528-4c96-9797-3d1f9729d5f9\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"f11kolo\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"7bb87fec-3534-413e-af57-b7ca122153a7\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"f11olokk\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"c7ec6339-950f-4529-896c-7162b4a27eb6\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"f11rtrtryyy\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"1cb24525-8145-41f7-9b00-6caa221d129b\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"f1oiooioi\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"d3095961-e0bc-498c-8c1e-535dad62836d\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"f11jkjkjkj\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"c7954660-9a1a-4618-b771-878d83a0128e\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"f11nmnmmmm\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"4126600a-fc7d-46b8-849a-729714443f91\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          }\n" +
            "        ],\n" +
            "        \"defaultIfAirlockSystemIsDown\": false,\n" +
            "        \"name\": \"F1\",\n" +
            "        \"namespace\": \"ns\",\n" +
            "        \"type\": \"FEATURE\",\n" +
            "        \"uniqueId\": \"312a420d-1299-4b26-ae59-70f076899e0d\",\n" +
            "        \"defaultConfiguration\": null,\n" +
            "        \"noCachedResults\": false\n" +
            "      },\n" +
            "      {\n" +
            "        \"features\": [\n" +
            "          {\n" +
            "            \"features\": [],\n" +
            "            \"defaultIfAirlockSystemIsDown\": false,\n" +
            "            \"name\": \"F112\",\n" +
            "            \"namespace\": \"ns\",\n" +
            "            \"type\": \"FEATURE\",\n" +
            "            \"uniqueId\": \"452df716-6014-4e1c-b106-abbbdb6c1cf0\",\n" +
            "            \"defaultConfiguration\": null,\n" +
            "            \"noCachedResults\": false\n" +
            "          }\n" +
            "        ],\n" +
            "        \"defaultIfAirlockSystemIsDown\": false,\n" +
            "        \"name\": \"F12\",\n" +
            "        \"namespace\": \"ns\",\n" +
            "        \"type\": \"FEATURE\",\n" +
            "        \"uniqueId\": \"0a2b5847-ba5a-4d95-a748-8679a2506ee7\",\n" +
            "        \"defaultConfiguration\": null,\n" +
            "        \"noCachedResults\": false\n" +
            "      },\n" +
            "      {\n" +
            "        \"features\": [],\n" +
            "        \"defaultIfAirlockSystemIsDown\": false,\n" +
            "        \"name\": \"F5UG55\",\n" +
            "        \"namespace\": \"ns\",\n" +
            "        \"type\": \"FEATURE\",\n" +
            "        \"uniqueId\": \"7fa84c87-cedc-4207-a0cb-43f1c650f76f\",\n" +
            "        \"defaultConfiguration\": null,\n" +
            "        \"noCachedResults\": false\n" +
            "      },\n" +
            "      {\n" +
            "        \"features\": [],\n" +
            "        \"defaultIfAirlockSystemIsDown\": false,\n" +
            "        \"name\": \"F5UG77\",\n" +
            "        \"namespace\": \"ns\",\n" +
            "        \"type\": \"FEATURE\",\n" +
            "        \"uniqueId\": \"8ab99b75-e769-4958-ae42-099766ea1fd3\",\n" +
            "        \"defaultConfiguration\": null,\n" +
            "        \"noCachedResults\": false\n" +
            "      }\n" +
            "    ],\n" +
            "    \"type\": \"ROOT\",\n" +
            "    \"uniqueId\": \"9155436b-1d07-42af-b726-1522fa8c71c2\"\n" +
            "  },\n" +
            "  \"version\": \"V2.5\",\n" +
            "  \"productName\": \"SimpleProdDebugger\"\n" +
            "}";
    public DebugToolTests() {
        defaults = strJson;
        productId = getProductId();
        encryptionKey = "CREWJPIGEFXTC9J8";
    }

    @Test
    public void runStreamsAndGetResults() {

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        String eventsArray = "[{ \"name\": \"detail-viewed\",\n" +
                "\"eventData\": { \"name\": \"go-run\", \"sub\": \"tomorrow\" } }\n" +
                "]";
        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/addEvents").queryParam("locale", "en_US").request().put(Entity.json(eventsArray));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/run").queryParam("locale", "en_US").queryParam("streamId", "stream1").request().put(Entity.json("[]"));
        Assert.assertEquals(response.getStatus(), 200);

        client.target(serverUrl + "/products/" + productInstanceId + "/streams/results").request().get(Map.class);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams").request().get();
        Assert.assertTrue(response.getStatus() == 200);
        List<Stream> streams = response.readEntity(new GenericType<List<Stream>>() {
        });
        Assert.assertTrue(streams.size() > 0);
        for (Stream stream: streams){
            boolean success = false;
            if (stream.getName().equals("stream")){
                if (stream.getResult().equals("{\"count\":5}")){
                    success = true;
                }else{
                    if (stream.getTrace()[0].contains("percentage")){
                        success = true;
                    }
                }
            }
            if (stream.getName().equals("Stream2")){
                if (stream.getResult().isEmpty()){
                    success = true;
                }
            }
            Assert.assertTrue(success);
        }
    }


    @Test
    public void setPercentageTest() {

        String featureName = "ns.F112";

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","features").queryParam("itemName", featureName).request().put(Entity.entity("true", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertTrue(response.getStatus() == 200);

        Boolean inRange = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","features").queryParam("itemName", featureName).request().get(Boolean.class);
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(inRange);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

//        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
//                request().put(Entity.json(""));
//        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","features").queryParam("itemName", featureName).request().put(Entity.entity("false", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertTrue(response.getStatus() == 200);

        inRange = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","features").queryParam("itemName", featureName).request().get(Boolean.class);
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertFalse(inRange);

        //Set experiment
//        response = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","experiments").queryParam("itemName", "exp4.var4").request().put(Entity.entity("true", MediaType.TEXT_PLAIN_TYPE));
//        Assert.assertTrue(response.getStatus() == 200);
//
//        inRange = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","experiments").queryParam("itemName", "exp4.var4").request().get(Boolean.class);
//        Assert.assertTrue(response.getStatus() == 200);
//        Assert.assertTrue(inRange);
//
//
//        //Set experiment
//        response = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","streams").queryParam("itemName", "s2").request().put(Entity.entity("true", MediaType.TEXT_PLAIN_TYPE));
//        Assert.assertTrue(response.getStatus() == 200);
//
//        inRange = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","streams").queryParam("itemName", "s2").request().get(Boolean.class);
//        Assert.assertTrue(response.getStatus() == 200);
//        Assert.assertTrue(inRange);

    }
}