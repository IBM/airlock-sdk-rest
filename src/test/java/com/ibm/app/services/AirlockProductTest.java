package com.ibm.app.services;

import com.ibm.airlock.rest.model.Product;
import org.glassfish.jersey.server.model.ParamQualifier;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.List;


public class AirlockProductTest extends BaseServiceTest {

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


    public  AirlockProductTest(){
        defaults = DEFAULTS;
        productId = getProductId();
    }

    @Test
    public void mutipleInitWithSameVerionsAndDefaultFile() {
        try {
            Product newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", "9").request().post(Entity.json(defaults), Product.class);
            Assert.assertNotNull(newProduct);
            Assert.assertEquals(newProduct.getProductId(), productId);
            Assert.assertEquals(newProduct.getAppVersion(), "9");
            newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", "10").request().post(Entity.json(defaults), Product.class);
            Assert.assertNotNull(newProduct);
            Assert.assertEquals(newProduct.getProductId(), productId);
            Assert.assertEquals(newProduct.getAppVersion(), "10");
            List<Product> productList = client.target(serverUrl + "/products").request().get(List.class);
            Assert.assertTrue( productList.size() > 0);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getProductByID() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "").request().get();
        Assert.assertEquals(200, response.getStatus());
        Product product = response.readEntity(Product.class);
        Assert.assertEquals(productId, product.getProductId());
        Assert.assertEquals("TestJavaSDK", product.getName());
    }


    @Test
    public void getProducts() {
        Product[] products = client.target(serverUrl + "/products").request().get(Product[].class);
        Assert.assertNotNull(products);
        Assert.assertTrue(products.length>0);
    }
}