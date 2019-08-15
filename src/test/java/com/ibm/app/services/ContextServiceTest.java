package com.ibm.app.services;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;


public class ContextServiceTest extends BaseServiceTest {

    private final static String DEFAULTS = "{\n" +
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


    public ContextServiceTest() {
        defaults = DEFAULTS;
        productId = getProductId();
    }


    @Test
    public void productContextShouldNotBeUpdatedBySharedContext() {

        Map context = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertTrue(context.size() == 0);

        Response response = client.target(serverUrl + "/products/context").request().put(Entity.entity("{\"context\":{\"field\":\"value\"}}", MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());
        context = client.target(serverUrl + "/products/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));

        context = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertTrue(context.size() == 0);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").
                queryParam("sync", true).request().
                put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE), Response.class);
        Assert.assertEquals(200, response.getStatus());

        context = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertTrue(context.size() == 0);

        context = client.target(serverUrl + "/products/" + productInstanceId + "/context/current").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertTrue(context.size() == 1);
    }

    @Test
    public void addSharedContextWithClearPreviousContextOff() {

        Response response = client.target(serverUrl + "/products/context").request().put(Entity.entity("{\"context\":{\"field\":\"value\"}}", MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());
        Map context = client.target(serverUrl + "/products/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));


        response = client.target(serverUrl + "/products/context").queryParam("clearPreviousContext", false).request().put(Entity.entity("{}", MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());

        context = client.target(serverUrl + "/products/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));

    }

    @Test
    public void addSharedContextWithClearPreviousContextOn() {


        Response response = client.target(serverUrl + "/products/context").request().put(Entity.entity("{\"context\":{\"field\":\"value\"}}", MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());
        Map context = client.target(serverUrl + "/products/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));

        deleteContext("/products/context");
        context = client.target(serverUrl + "/products/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{}")).toString(), new Gson().toJson(context).toString());
    }



    @Test
    public void addProductContextWithClearPreviousContextOn() {

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().put(Entity.entity("{\"context\":{\"field\":\"value\"}}", MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());
        Map context = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));

        deleteContext("/products/" + productInstanceId + "/context");
        context = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));
    }


    @Test
    public void addProductContextWithClearPreviousContextOff() {

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().put(Entity.entity("{\"context\":{\"field\":\"value\"}}", MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());
        Map context = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));


        response = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().put(Entity.entity("{}", MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());

        response = client.target(serverUrl + "/products/" + productInstanceId + "/context").request().get();
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));

    }

    @Test
    public void getLastContext() {


        deleteContext("/products/context");
        putContext("/products/" + productInstanceId + "/context", "{\"context\":{\"field1\":\"value1\"}}");

        Map context = client.target(serverUrl + "/products/" + productInstanceId + "/context/current").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field1\":\"value1\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").
                queryParam("sync", true).request().
                put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE), Response.class);
        Assert.assertEquals(200, response.getStatus());

        putContext("/products/context", "{\"context\":{\"field3\":\"value3\"}}");
        putContext("/products/" + productInstanceId + "/context", "{\"context\":{\"field2\":\"value2\"}}");


        context = getContext("/products/" + productInstanceId + "/context/last-calculated");
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field1\":\"value1\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));
    }

    @Test
    public void doubleUdateCurrentContextShouldNotChangeIt() {

        putContext("/products/" + productInstanceId + "/context", "{\"context\":[\"value1\",\"value2\"]}");
        putContext("/products/" + productInstanceId + "/context", "{\"context\":[\"value1\",\"value2\"]}");

        Map context = getContext("/products/" + productInstanceId + "/context");
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":[\"value1\",\"value2\"]}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));

        context = getContext("/products/context");
        putContext("/products/context", "{\"context\":[\"value3\",\"value4\"]}");
        putContext("/products/context", "{\"context\":[\"value3\",\"value4\"]}");

        context = getContext("/products/context");
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":[\"value3\",\"value4\"]}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));


    }

    @Test
    public void getCurrentWithSharedContext() {

        putContext("/products/context", "{\"context\":{\"field\":\"value\"}}");
        putContext("/products/" + productInstanceId + "/context", "{\"context\":{\"field1\":\"value1\"}}");


        Map context = client.target(serverUrl + "/products/" + productInstanceId + "/context/current").request().get(Map.class);
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\",\"field1\":\"value1\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").
                queryParam("sync", true).request().
                put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE), Response.class);
        Assert.assertEquals(200, response.getStatus());


        context = getContext("/products/" + productInstanceId + "/context/current");
        Assert.assertNotNull(context);
        Assert.assertEquals((new JSONObject("{\"context\":{\"field\":\"value\",\"field1\":\"value1\"}}").toString()), (new JSONObject(new Gson().toJson(context)).toString()));
    }


    private void putContext(String path, String context) {
        Response response = client.target(serverUrl + path).request().
                put(Entity.entity(context, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());
    }

    private Map getContext(String path) {
        Map context = client.target(serverUrl + path).request().get(Map.class);
        Assert.assertNotNull(context);
        return context;
    }
}