package com.ibm.app.services;

import com.ibm.airlock.rest.common.InstancesRetentionService;
import com.ibm.airlock.rest.model.Product;
import com.ibm.airlock.rest.server.GnomeAirlockRest;
import org.junit.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.junit.Assert.*;

public class InstancesRetentionServiceTest extends BaseServiceTest {

    boolean propertiesSet = false;
    static boolean skipAllTests = false;

    public InstancesRetentionServiceTest() {
        defaults = "{\"devS3Path\":\"https:\\/\\/airlockstorage.blob.core.windows.net\\/dev1\\/\",\"defaultLanguage\":\"en\",\"productId\":\"22ffb8fc-e8e8-4231-b1b7-bf9fee3ff87d\",\"s3Path\":\"https:\\/\\/airlockstorage.blob.core.windows.net\\/dev1\",\"supportedLanguages\":[\"en\"],\"seasonId\":\"b8f2410b-3113-4aa6-a1ae-2cf47e5bc19c\",\"root\":{\"features\":[{\"features\":[{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Feed\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"e407dcef-6423-45ca-9ed2-856fdec19cb6\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Feed\\\",\\n\\t\\\"image\\\":1\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Events\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"2f046b87-6fe9-485e-b078-c755cb69943e\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Events\\\",\\n\\t\\\"image\\\":2\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Favorites\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"3553bb1e-4aad-4fba-a325-03b55340c910\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Favorites\\\",\\n\\t\\\"image\\\":3\\n}\",\"noCachedResults\":false}],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"bottomNav\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"18c5519a-1fdf-47cb-8208-5c2fcaecb610\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\" : \\\"title1\\\",\\n\\t\\\"image\\\" : 1\\n}\",\"noCachedResults\":false},{\"features\":[{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"story1\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"b2012efa-f9d7-4446-8eac-f459dcf13987\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Story 1\\\",\\n\\t\\\"image\\\":1,\\n\\t\\\"imageUrle\\\":\\\"\\\",\\n\\t\\\"shortDesc\\\": \\\"this is a very special story about somebody very special\\\"\\n\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"story2\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"9baaf0b3-b40f-4863-8871-e83aa7475530\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Story 2\\\",\\n\\t\\\"image\\\":2,\\n\\t\\\"imageUrle\\\":\\\"\\\",\\n\\t\\\"shortDesc\\\": \\\"this is a story about climbing the Everest\\\"\\n}\",\"noCachedResults\":false}],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"storiesRoot\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"dcf860bd-828e-4210-8627-b19680545117\",\"defaultConfiguration\":null,\"noCachedResults\":false}],\"type\":\"ROOT\",\"uniqueId\":\"c7108b23-a235-4c1a-96a5-da8186e13499\"},\"version\":\"V2.5\",\"productName\":\"SampleForEitan\"}";
        productId = getProductId();
        encryptionKey = "N5AOMEJNGKEBMSEM";
    }


    @Before
    public void resetAndInit() {
        try {
            if (!propertiesSet) {
                InstancesRetentionService.getInstance().updateMinimumTimeToLive(5);
                InstancesRetentionService.getInstance().updateMaxActiveProductInstances(1);
                InstancesRetentionService.getInstance().updateInterval(10);
                propertiesSet = true;
            }
        } catch (Exception e) {
            skipAllTests = true;
        }
    }

    @Test
    public void retenationAfterCreateOnly() throws InterruptedException {

        if (skipAllTests) {
            return;
        }

        Locale.setDefault(new Locale("en", "US"));
        resetAllProducts();

        Product newProduct1 = client.target(serverUrl + "/products/init").queryParam("appVersion", appVersion).queryParam("encryptionKey", encryptionKey)
                .request().post(Entity.json(defaults), Product.class);
        Assert.assertNotNull(newProduct1);
        Assert.assertNotNull(newProduct1.getProductId(), productId);


        Thread.sleep(5 * 1000);

        Product newProduct2 = client.target(serverUrl + "/products/init").queryParam("appVersion", appVersion).queryParam("encryptionKey", encryptionKey)
                .request().post(Entity.json(defaults), Product.class);
        Assert.assertNotNull(newProduct2);
        Assert.assertNotNull(newProduct2.getProductId(), productId);

        GenericType<List<Product>> gType = new GenericType<List<Product>>() {
        };
        List<Product> products = client.target(serverUrl + "/products/").request().get(gType);
        Assert.assertEquals(2, products.size());

        Thread.sleep(15 * 1000);

        products = client.target(serverUrl + "/products/").request().get(gType);

        Assert.assertEquals(1, products.size());
    }


    @AfterClass
    public static void resetSystemProperties() {
        if (!skipAllTests) {
            //back to defaults
            InstancesRetentionService.getInstance().updateMinimumTimeToLive(60 * 24 * 60);
            InstancesRetentionService.getInstance().updateMaxActiveProductInstances(5);
            InstancesRetentionService.getInstance().updateInterval(10 * 60);
        }
    }
}