package com.ibm.app.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


public class SecuredConnectionTest extends BaseServiceTest{

    private static final String DEFAULTS_NAME = "QA_SecureConnection.json";

    protected String getDefaultFileName() {
        return DEFAULTS_NAME;
    }


    public SecuredConnectionTest() {
        defaults = getDefaults();
        productId = getProductId();
        encryptionKey = "KRPD4SU1UQRCNRUR";
    }

    @Before
    public void setUserGroup() {
        try {
            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"dev\"]"));
            Assert.assertEquals(200, response.getStatus());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getAllUserGroups() {
        List<String> allProductUserGroups = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups/all").request().get(List.class);
        Assert.assertNotNull(allProductUserGroups);
        Assert.assertEquals(allProductUserGroups.size(),2);
    }
}
