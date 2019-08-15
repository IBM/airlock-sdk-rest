package com.ibm.app.services;

import com.ibm.airlock.rest.model.Feature;
import com.ibm.airlock.rest.model.Product;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;



public class AirlockProductWrongActionsTest extends BaseServiceTest {

    public AirlockProductWrongActionsTest() {
        defaults = "{\"devS3Path\":\"https:\\/\\/airlockstorage.blob.core.windows.net\\/dev1\\/\",\"defaultLanguage\":\"en\",\"productId\":\"22ffb8fc-e8e8-4231-b1b7-bf9fee3ff87d\",\"s3Path\":\"https:\\/\\/airlockstorage.blob.core.windows.net\\/dev1\",\"supportedLanguages\":[\"en\"],\"seasonId\":\"b8f2410b-3113-4aa6-a1ae-2cf47e5bc19c\",\"root\":{\"features\":[{\"features\":[{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Feed\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"e407dcef-6423-45ca-9ed2-856fdec19cb6\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Feed\\\",\\n\\t\\\"image\\\":1\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Events\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"2f046b87-6fe9-485e-b078-c755cb69943e\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Events\\\",\\n\\t\\\"image\\\":2\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Favorites\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"3553bb1e-4aad-4fba-a325-03b55340c910\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Favorites\\\",\\n\\t\\\"image\\\":3\\n}\",\"noCachedResults\":false}],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"bottomNav\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"18c5519a-1fdf-47cb-8208-5c2fcaecb610\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\" : \\\"title1\\\",\\n\\t\\\"image\\\" : 1\\n}\",\"noCachedResults\":false},{\"features\":[{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"story1\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"b2012efa-f9d7-4446-8eac-f459dcf13987\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Story 1\\\",\\n\\t\\\"image\\\":1,\\n\\t\\\"imageUrle\\\":\\\"\\\",\\n\\t\\\"shortDesc\\\": \\\"this is a very special story about somebody very special\\\"\\n\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"story2\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"9baaf0b3-b40f-4863-8871-e83aa7475530\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Story 2\\\",\\n\\t\\\"image\\\":2,\\n\\t\\\"imageUrle\\\":\\\"\\\",\\n\\t\\\"shortDesc\\\": \\\"this is a story about climbing the Everest\\\"\\n}\",\"noCachedResults\":false}],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"storiesRoot\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"dcf860bd-828e-4210-8627-b19680545117\",\"defaultConfiguration\":null,\"noCachedResults\":false}],\"type\":\"ROOT\",\"uniqueId\":\"c7108b23-a235-4c1a-96a5-da8186e13499\"},\"version\":\"V2.5\",\"productName\":\"SampleForEitan\"}";
        productId = getProductId();
        encryptionKey = "N5AOMEJNGKEBMSEM";
    }

    @Test
    public void addAndRemoveUserGroupsToProduct() {
        Response response = client.target(serverUrl + "/products/kkkk" + productInstanceId + "/usergroups").request().put(Entity.json("[\"Adina\"]"));
        Assert.assertEquals(400, response.getStatus());

        response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("notValidInput"));
        Assert.assertEquals(400, response.getStatus());


        response = client.target(serverUrl + "/products/kkk" + productInstanceId + "/usergroups").request().get();
        Assert.assertEquals(400, response.getStatus());
    }
    @Test
    public void pullNotExistsProduct() {
        Response response = client.target(serverUrl + "/products/kkkk" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(400,response.getStatus());

        Long lastPull = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().get(Long.class);
        Assert.assertNotNull(lastPull);
        
    }

    @Test
    public void pullAllProductsWithPostInsteadOfPut() {
        Response response = client.target(serverUrl + "/products/pullAll").request().post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 405);
    }
   
    @Test
    public void calculateNotExistsProduct() {
        Response response = client.target(serverUrl + "/products/kkkk" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json(""));
        Assert.assertEquals(400,response.getStatus());
    }

    
    @Test
    public void getFeatureNotExists() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);


        Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/modules.Airlock Control Over Modules999").request().get(Feature.class);
        Assert.assertNotNull(feature);
        Assert.assertEquals(feature.getDebugInfo().getSource(), com.ibm.airlock.common.data.Feature.Source.MISSING);
        Assert.assertEquals(feature.isOn(), false);
    }
    @Test
    public void checkNotExistsAction() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/kuku/bigKuku").queryParam("sync", true).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 404);

    }
    @Test
    public void getLastCalculate() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);
        // wait a bit
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        Long lastCalculate = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").request().get(Long.class);
        Assert.assertNotNull(lastCalculate);
        Assert.assertTrue(lastCalculate > 0);

    }

    @Test
    public void getStringByKeyNotFound() {

        String stringId = "DDI.FoggyNotFound";
            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/strings/" + stringId).request().put(Entity.json("[]"));
            Assert.assertEquals(404,response.getStatus());    
    }
    @Test
    public void syncNotExists() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", false).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);

        Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/tabs.bottomNav").request().get(Feature.class);
        Assert.assertNotNull(feature);
        Assert.assertEquals(com.ibm.airlock.common.data.Feature.Source.DEFAULT, feature.getDebugInfo().getSource());

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/modules.kukuNotExists").request().get(Feature.class);
        Assert.assertNotNull(feature);
        Assert.assertEquals(com.ibm.airlock.common.data.Feature.Source.MISSING, feature.getDebugInfo().getSource());

        response = client.target(serverUrl + "/products/kkkkk" + productInstanceId + "/sync").
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 400);

    }


}