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

import static org.junit.Assert.*;


@Ignore
public class StreamsServiceTest extends BaseServiceTest{

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

    public StreamsServiceTest() {
        defaults = strJson;
        productId = getProductId();
    }
    @Test
    public void runStreamsAndGetResults() {

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"Adina\"]"));
        Assert.assertEquals(200, response.getStatus());

        response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        JSONArray events = new JSONArray();
        String eventStr = "{\n" +
                "  \"dateTime\": 1504621642364,\n" +
                "  \"eventData\": {\n" +
                "    \"method\": \"ftl\",\n" +
                "    \"foreground\": true\n" +
                "  },\n" +
                "  \"name\": \"app-launch\"\n" +
                "}";
        JSONObject event = new JSONObject(eventStr);
        events.put(event);
        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/addEvents").queryParam("locale", "en_US").request().put(Entity.json(events.toString()));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/run").queryParam("locale", "en_US").queryParam("streamId", "appLaunchStream").request().put(Entity.json("[]"));
        Assert.assertEquals(response.getStatus(), 200);

        Map streamResults = client.target(serverUrl + "/products/" + productInstanceId + "/streams/results").request().get(Map.class);
        Assert.assertTrue(streamResults!= null);
        Map values = (Map) streamResults.get("appLaunchStream");
        Assert.assertTrue(values!= null);
        Assert.assertEquals(1.0, values.get("appLaunchCounter"));

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/run").queryParam("locale", "en_US").request().put(Entity.json(events.toString()));
        Assert.assertEquals(response.getStatus(), 200);

        streamResults = client.target(serverUrl + "/products/" + productInstanceId + "/streams/results").request().get(Map.class);
        Assert.assertTrue(streamResults!= null);
        values = (Map) streamResults.get("appLaunchStream");
        Assert.assertEquals(2.0, values.get("appLaunchCounter"));

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/run").queryParam("locale", "en_US").request().put(Entity.json(events.toString()));
        Assert.assertEquals(response.getStatus(), 200);

        streamResults = client.target(serverUrl + "/products/" + productInstanceId + "/streams/results").request().get(Map.class);
        Assert.assertTrue(streamResults!= null);
        values = (Map) streamResults.get("appLaunchStream");
        Assert.assertEquals(3.0, values.get("appLaunchCounter"));

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/run").queryParam("locale", "en_US").request().put(Entity.json(events.toString()));
        Assert.assertEquals(response.getStatus(), 200);


        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams").request().get();
        Assert.assertTrue(response.getStatus() == 200);
        List<Stream> streams = response.readEntity(new GenericType<List<Stream>>() {
        });

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/testStreamWithPercentage/actions/clearTrace").queryParam("locale", "en_US").request().put(Entity.json(events.toString()));
        Assert.assertTrue(response.getStatus() == 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams").request().get();
        Assert.assertTrue(response.getStatus() == 200);
        streams = response.readEntity(new GenericType<List<Stream>>() {
        });

        for (Stream streamItem: streams){
            if (streamItem.getName().equals("testStreamWithPercentage")){
                String[] traceArray = streamItem.getTrace();
                Assert.assertTrue(traceArray == null || traceArray.length == 0 || streamItem.getTrace()[0] == null);
            }
        }

        //reset the stream data and check that data was cleaned out (cache, events etc...)
        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/appLaunchStream/actions/reset").queryParam("locale", "en_US").request().put(Entity.json(events.toString()));
        Assert.assertEquals(response.getStatus(), 200);


        streamResults = client.target(serverUrl + "/products/" + productInstanceId + "/streams/results").request().get(Map.class);
        Assert.assertTrue(streamResults!= null);

        Assert.assertTrue(streamResults.size() == 0 || ((Map) streamResults.get("appLaunchStream")).size() == 0);

    }

    @Test
    public void resetStreamSettings() {

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"Adina\"]"));
        Assert.assertEquals(200, response.getStatus());

        response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/appLaunchStream/actions/reset").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/appLaunchStream/actions/clearTrace").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/streams/appLaunchStream/actions/suspend").queryParam("doSuspend", true).request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        Map streamResults = client.target(serverUrl + "/products/" + productInstanceId + "/streams/results").request().get(Map.class);
        Assert.assertTrue(streamResults != null);
        Object values = streamResults.get("appLaunchStream");
        Assert.assertTrue(values == null);
    }
}