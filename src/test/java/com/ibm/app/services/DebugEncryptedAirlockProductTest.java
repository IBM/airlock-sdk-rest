package com.ibm.app.services;

import com.ibm.airlock.rest.model.Feature;
import com.ibm.airlock.rest.model.Stream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;


public class DebugEncryptedAirlockProductTest extends BaseServiceTest {


    private static final String DEFAULTS_NAME = "AmirProd.json";

    protected String getDefaultFileName() {
        return DEFAULTS_NAME;
    }


    public DebugEncryptedAirlockProductTest() {
        defaults = getDefaults();
        productId = getProductId();
        encryptionKey = "TNHI3XTLNXCMDIZ6";
    }

    @Test
    public void getProductBranchesTest() {
        try {
            List<String> data = client.target(serverUrl + "/products/" + productInstanceId + "/branches").request().get(List.class);
            Assert.assertTrue(data.size() >= 2);
            int expectedBranchFound = 0;
            for (String branch : data) {
                if (branch.equals("a1") || branch.equals("a2")) {
                    expectedBranchFound++;
                }
            }
            Assert.assertTrue(expectedBranchFound == 2);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    @Test
    public void getProductSelectedBranchTest() {
        try {
            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/branches/a1").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
            Assert.assertTrue(response.getStatus() == 200);

            String selectedBranch = client.target(serverUrl + "/products/" + productInstanceId + "/branch").request().get(String.class);
            Assert.assertNotNull(selectedBranch);
            Assert.assertEquals("a1", selectedBranch);

            response = client.target(serverUrl + "/products/" + productInstanceId + "/branches/a2").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
            Assert.assertTrue(response.getStatus() == 200);

            selectedBranch = client.target(serverUrl + "/products/" + productInstanceId + "/branch").request().get(String.class);
            Assert.assertNotNull(selectedBranch);
            Assert.assertEquals("a2", selectedBranch );

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void setPercentageTest() {

        String featureName = "ns.f4";

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

        //Set experiment
        response = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","experiments").queryParam("itemName", "exp4.var4").request().put(Entity.entity("true", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertTrue(response.getStatus() == 200);

        inRange = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","experiments").queryParam("itemName", "exp4.var4").request().get(Boolean.class);
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(inRange);


        //Set streams
        response = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","streams").queryParam("itemName", "s2").request().put(Entity.entity("true", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertTrue(response.getStatus() == 200);

        inRange = client.target(serverUrl + "/products/" + productInstanceId + "/percentage").queryParam("section","streams").queryParam("itemName", "s2").request().get(Boolean.class);
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(inRange);

    }
}