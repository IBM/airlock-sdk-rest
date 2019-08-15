package com.ibm.app.services;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.List;


public class UserGroupsEncryptedProductTest extends BaseServiceTest {

    private static final String DEFAULTS_NAME = "QA_SecureConnection.json";

    protected String getDefaultFileName() {
        return DEFAULTS_NAME;
    }


    public UserGroupsEncryptedProductTest() {
        defaults = getDefaults();
        productId = getProductId();
        encryptionKey = "KRPD4SU1UQRCNRUR";
    }

    @Test
    public void getServerUserGroupList() {
        List<String> userGroups = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups/all").request().get(List.class);

        Assert.assertTrue(userGroups.size() > 1);
        boolean activationGroupFound = false;
        for (String groupName: userGroups){
            if (groupName.equals("dev")){
                activationGroupFound = true;
                break;
            }
        }
        Assert.assertTrue(activationGroupFound);

    }

}
