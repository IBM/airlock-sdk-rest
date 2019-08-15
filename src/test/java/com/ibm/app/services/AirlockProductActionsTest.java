package com.ibm.app.services;

import com.ibm.airlock.rest.model.Feature;
import com.ibm.airlock.rest.model.Product;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.airlock.sdk.config.ConfigurationManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;


public class AirlockProductActionsTest extends BaseServiceTest {

    public AirlockProductActionsTest() {
        defaults = "{\"devS3Path\":\"https:\\/\\/airlockstorage.blob.core.windows.net\\/dev1\\/\",\"defaultLanguage\":\"en\",\"productId\":\"22ffb8fc-e8e8-4231-b1b7-bf9fee3ff87d\",\"s3Path\":\"https:\\/\\/airlockstorage.blob.core.windows.net\\/dev1\",\"supportedLanguages\":[\"en\"],\"seasonId\":\"b8f2410b-3113-4aa6-a1ae-2cf47e5bc19c\",\"root\":{\"features\":[{\"features\":[{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Feed\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"e407dcef-6423-45ca-9ed2-856fdec19cb6\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Feed\\\",\\n\\t\\\"image\\\":1\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Events\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"2f046b87-6fe9-485e-b078-c755cb69943e\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Events\\\",\\n\\t\\\"image\\\":2\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"Favorites\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"3553bb1e-4aad-4fba-a325-03b55340c910\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Favorites\\\",\\n\\t\\\"image\\\":3\\n}\",\"noCachedResults\":false}],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"bottomNav\",\"namespace\":\"tabs\",\"type\":\"FEATURE\",\"uniqueId\":\"18c5519a-1fdf-47cb-8208-5c2fcaecb610\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\" : \\\"title1\\\",\\n\\t\\\"image\\\" : 1\\n}\",\"noCachedResults\":false},{\"features\":[{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"story1\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"b2012efa-f9d7-4446-8eac-f459dcf13987\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Story 1\\\",\\n\\t\\\"image\\\":1,\\n\\t\\\"imageUrle\\\":\\\"\\\",\\n\\t\\\"shortDesc\\\": \\\"this is a very special story about somebody very special\\\"\\n\\n}\",\"noCachedResults\":false},{\"features\":[],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"story2\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"9baaf0b3-b40f-4863-8871-e83aa7475530\",\"defaultConfiguration\":\"{\\n\\t\\\"title\\\":\\\"Story 2\\\",\\n\\t\\\"image\\\":2,\\n\\t\\\"imageUrle\\\":\\\"\\\",\\n\\t\\\"shortDesc\\\": \\\"this is a story about climbing the Everest\\\"\\n}\",\"noCachedResults\":false}],\"defaultIfAirlockSystemIsDown\":false,\"name\":\"storiesRoot\",\"namespace\":\"stories\",\"type\":\"FEATURE\",\"uniqueId\":\"dcf860bd-828e-4210-8627-b19680545117\",\"defaultConfiguration\":null,\"noCachedResults\":false}],\"type\":\"ROOT\",\"uniqueId\":\"c7108b23-a235-4c1a-96a5-da8186e13499\"},\"version\":\"V2.5\",\"productName\":\"SampleForEitan\"}";
        productId = getProductId();
        encryptionKey = "N5AOMEJNGKEBMSEM";
    }

    @Before
    public void setUserGroup() {
        try {
            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"DEMO\"]"));
            Assert.assertEquals(200, response.getStatus());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void pull() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(412, response.getStatus());

        Long lastPull = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().get(Long.class);
        Assert.assertNotNull(lastPull);
        Assert.assertTrue(lastPull > 0);
    }

    @Test
    public void pullAllProducts() {
        Response response = client.target(serverUrl + "/products/pullAll").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void calculate() {

        Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/tabs.bottomNav").request().get(Feature.class);
        Assert.assertTrue(feature == null || feature.getChildren().size() == 3);

        String cacheResponse = client.target(serverUrl + "/products/" + productInstanceId + "/cache").request().delete(String.class);
        Assert.assertTrue(cacheResponse != null);
        Assert.assertEquals(cacheResponse, "");

        resetAndInit();
        setUserGroup();

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/tabs.bottomNav").request().get(Feature.class);
        Assert.assertNotNull(feature);
        Assert.assertEquals(com.ibm.airlock.common.data.Feature.Source.DEFAULT, feature.getDebugInfo().getSource());

        //now call calculate with sync
        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/tabs.bottomNav").request().get(Feature.class);
        Assert.assertNotNull(feature);
        Assert.assertEquals(com.ibm.airlock.common.data.Feature.Source.SERVER, feature.getDebugInfo().getSource());

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/tabs.bottomNav").request().get(Feature.class);
        Assert.assertTrue(feature != null);

        Assert.assertTrue(feature.isOn());
        Assert.assertTrue(feature.getChildren().size() == 3);

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/root").request().get(Feature.class);
        Assert.assertTrue(feature != null);
        Assert.assertTrue(feature.getChildren().size() == 2);

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
    public void calculateBeforePull() {

        String cacheResponse = client.target(serverUrl + "/products/" + productInstanceId + "/cache").request().delete(String.class);
        Assert.assertTrue(cacheResponse != null);
        Assert.assertEquals(cacheResponse, "");

        resetAndInit();

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);

        Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/tabs.bottomNav").request().get(Feature.class);
        Assert.assertNotNull(feature);
        Assert.assertEquals(com.ibm.airlock.common.data.Feature.Source.DEFAULT, feature.getDebugInfo().getSource());
    }

    @Test
    public void sync() {
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

        response = client.target(serverUrl + "/products/" + productInstanceId + "/sync").
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/tabs.bottomNav").request().get(Feature.class);
        Assert.assertNotNull(feature);
        Assert.assertEquals(com.ibm.airlock.common.data.Feature.Source.SERVER, feature.getDebugInfo().getSource());
    }

    @Test
    public void getLastSyncTime() {

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", false).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/sync").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        Long lastPull = client.target(serverUrl + "/products/" + productInstanceId + "/sync").request().get(Long.class);
        Assert.assertNotNull(lastPull);
        Assert.assertTrue(lastPull > 0);

    }


    @Test
    public void getLastLocale() {

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json(""));
        Assert.assertEquals(response.getStatus(), 200);

        String lastLocale = client.target(serverUrl + "/products/" + productInstanceId + "/locale").request().get(String.class);
        Assert.assertNotNull(lastLocale);
        Assert.assertEquals("en_US", lastLocale);

    }

    @Test
    public void getLastPullTimeShouldNotResetAfterReInit() {

        Long lastPull = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().get(Long.class);
        Assert.assertNotNull(lastPull);
        Assert.assertTrue(lastPull == 0);

        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);

        Long lastPullTimeAfterPull = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().get(Long.class);
        Assert.assertNotNull(lastPullTimeAfterPull);
        Assert.assertTrue(lastPullTimeAfterPull > 0);

        Product newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", appVersion).request().post(Entity.json(defaults), Product.class);
        Assert.assertNotNull(newProduct);
        Assert.assertNotNull(newProduct.getProductId(), productId);
        this.productInstanceId = newProduct.getInstanceId();

        Long lastPullTimeAfterReinit = client.target(serverUrl + "/products/" + productInstanceId + "/pull").request().get(Long.class);
        Assert.assertNotNull(lastPull);
        Assert.assertTrue(lastPullTimeAfterReinit.equals(lastPullTimeAfterPull));
    }


    @Test
    public void testCopyToSSDDisk() {
        try {
            injectEnvironmentVariable(ConfigurationManager.CACHE_VOLUME, new File("cache").getAbsolutePath());
            injectEnvironmentVariable(ConfigurationManager.SSD_CACHE_VOLUME, new File("SSD").getAbsolutePath());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        try {
            AirlockMultiProductsManager.getInstance().copyCacheToSSD();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        File ssdFolder = new File(new File("SSD").getAbsolutePath());
        Assert.assertEquals(ssdFolder.listFiles().length,1);

        try {
            injectEnvironmentVariable(ConfigurationManager.CACHE_VOLUME, "");
            injectEnvironmentVariable(ConfigurationManager.SSD_CACHE_VOLUME, "");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private static void injectEnvironmentVariable(String key, String value)
            throws Exception {

        Class<?> processEnvironment = Class.forName("java.lang.ProcessEnvironment");

        Field unmodifiableMapField = getAccessibleField(processEnvironment, "theUnmodifiableEnvironment");
        Object unmodifiableMap = unmodifiableMapField.get(null);
        injectIntoUnmodifiableMap(key, value, unmodifiableMap);

        Field mapField = getAccessibleField(processEnvironment, "theEnvironment");
        Map<String, String> map = (Map<String, String>) mapField.get(null);
        map.put(key, value);
    }

    private static Field getAccessibleField(Class<?> clazz, String fieldName)
            throws NoSuchFieldException {

        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    private static void injectIntoUnmodifiableMap(String key, String value, Object map)
            throws ReflectiveOperationException {

        Class unmodifiableMap = Class.forName("java.util.Collections$UnmodifiableMap");
        Field field = getAccessibleField(unmodifiableMap, "m");
        Object obj = field.get(map);
        ((Map<String, String>) obj).put(key, value);
    }
}
