package com.ibm.app.services;

import com.ibm.airlock.rest.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;


public class MismatchSeasonTest extends BaseServiceTest{

    private static final String DEFAULTS_NAME = "QA_MismatchVesion.json";
    protected String getDefaultFileName() {
        return DEFAULTS_NAME;
    }

    public void resetAndInit() {
        Locale.setDefault(new Locale("en", "US"));
        resetAllProducts();
        try {
            Product newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", "5.0").queryParam("encryptionKey", encryptionKey)
                    .request().post(Entity.json(defaults), Product.class);
            Assert.assertNotNull(newProduct);
            Assert.assertNotNull(newProduct.getProductId(), productId);
            this.productInstanceId = newProduct.getInstanceId();

            Response response = client.target(serverUrl + "/products/" + this.productInstanceId + "/usergroups").request().put(Entity.json("[]"));
            Assert.assertEquals(200, response.getStatus());

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    public MismatchSeasonTest() {
        defaults = getDefaults();
        productId = getProductId();
        encryptionKey = "DMCIHSB1PAWFBNLX";
    }


    @Test
    public void pullCorrespondingSeasonToGivenAppVersion() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        Long lastPullTimeAfterPull = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().get(Long.class);
        Assert.assertNotNull(lastPullTimeAfterPull);
        Assert.assertTrue(lastPullTimeAfterPull > 0);
    }
}
