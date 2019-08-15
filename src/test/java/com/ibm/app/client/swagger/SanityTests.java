package com.ibm.app.client.swagger;

import com.ncp.airlock.client.ContextApi;
import com.ncp.airlock.client.FeaturesApi;
import com.ncp.airlock.client.UserGroupsApi;
import com.ncp.airlock.client.ActionsApi;
import com.ncp.airlock.client.ProductsApi;
import com.ncp.airlock.client.invoker.ApiException;
import com.ncp.airlock.client.model.Feature;
import com.ncp.airlock.client.model.Product;
import com.google.gson.Gson;
import org.junit.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

public class SanityTests {

    private final ProductsApi api = new ProductsApi();
    private final ActionsApi actionApi = new ActionsApi();
    private final UserGroupsApi userGroupsApi = new UserGroupsApi();
    private final ContextApi contextApi = new ContextApi();
    private final FeaturesApi featureApi = new FeaturesApi();
    String encryptionKey = "0ADVKET2SPQ7C0IH";
    String appVersion = "9.0";
    private Product product;


    @After
    public void tearDown() {
        try {
            Thread.sleep(1000);
            api.delete(product.getInstanceId());
        } catch (Exception e) {
            //do nothing
        }
    }

    @Before
    public void initTest() throws ApiException {
        product = createProduct();
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getAppVersion(), "9.0");
    }

    @Test
    public void endToEndTest() throws ApiException {
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getAppVersion(), "9.0");
        Assert.assertNotNull(product.getInstanceId());
    }


    private Product createProduct() throws ApiException {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, String> myMap = new Gson().fromJson(getDefaults(), type);
        return api.init(appVersion, myMap, encryptionKey);
    }


    @Test
    public void deleteProductTest() throws ApiException {
        String instanceIdTobeDeleteded = product.getInstanceId();
        Assert.assertNotNull(instanceIdTobeDeleteded);

        api.delete(instanceIdTobeDeleteded);

        try {
            api.getProductByID(instanceIdTobeDeleteded);
            Assert.fail("no exception thrown");
        } catch (ApiException e) {
            Assert.assertEquals(404, e.getCode());
        }
    }


    private Map<String, Object> createJSONParam(String value) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }

    @Test
    public void getCalcTimeAfterCalcTest() throws ApiException {
        String instanceId = product.getInstanceId();
        Assert.assertNotNull(instanceId);
        actionApi.calculate(instanceId, true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(0, Long.parseLong(actionApi.getLastCalculate(instanceId)));
    }

    @Test
    public void testSharedContext() throws ApiException {
        String instanceId = product.getInstanceId();
        Assert.assertNotNull(instanceId);
        actionApi.pull(instanceId, Locale.getDefault().toString());
        actionApi.calculate(instanceId, true);
    }


    @Test
    public void calcWithContextTest() throws ApiException {
        String instanceId = product.getInstanceId();
        Assert.assertNotNull(instanceId);

        //set user group
        userGroupsApi.updateUserGroupsToProduct(instanceId, Arrays.asList(new String[]{"SampleAppDEV"}));
        actionApi.pull(instanceId, Locale.getDefault().toString());
        contextApi.updateProductContext(instanceId, true, createJSONParam("{\"vehicle\":{\"speed\":20}}"));
        actionApi.calculate(instanceId, true);

        Feature feature = featureApi.getFeatureByName(instanceId, "ui.Tabs");
        Assert.assertTrue(feature.isOn());
        Assert.assertNotEquals(0, Long.parseLong(actionApi.getLastCalculate(instanceId)));
    }

    @Test
    public void calcWithSharedContextTest() throws ApiException {
        String instanceId = product.getInstanceId();
        Assert.assertNotNull(instanceId);

        //set user group
        userGroupsApi.updateUserGroupsToProduct(instanceId, Arrays.asList(new String[]{"SampleAppDEV"}));
        actionApi.pull(instanceId, Locale.getDefault().toString());
        contextApi.updateSharedContext(createJSONParam("{\"vehicle\":{\"position\":1}}"));
        actionApi.calculate(instanceId, true);

        Feature feature = featureApi.getFeatureByName(instanceId, "ui.Theme");
        Assert.assertTrue(!feature.isOn());

        contextApi.updateSharedContext(createJSONParam("{\"vehicle\":{\"position\":3}}"));
        actionApi.calculate(instanceId, true);

        feature = featureApi.getFeatureByName(instanceId, "ui.Theme");
        Assert.assertTrue(feature.isOn());

        Assert.assertNotEquals(0, Long.parseLong(actionApi.getLastCalculate(instanceId)));
    }


    @Test
    public void calcWithSharedAndProductContextTest() throws ApiException {
        String instanceId = product.getInstanceId();
        Assert.assertNotNull(instanceId);

        //set user group
        userGroupsApi.updateUserGroupsToProduct(instanceId, Arrays.asList(new String[]{"SampleAppDEV"}));
        actionApi.pull(instanceId, Locale.getDefault().toString());
        contextApi.updateSharedContext(createJSONParam("{\"vehicle\":{\"position\":1}}"));
        actionApi.calculate(instanceId, true);
        Feature feature = featureApi.getFeatureByName(instanceId, "ui.Theme");
        Assert.assertTrue(!feature.isOn());


        contextApi.updateSharedContext(createJSONParam("{\"vehicle\":{\"position\":3}}"));
        actionApi.calculate(instanceId, true);
        feature = featureApi.getFeatureByName(instanceId, "ui.Theme");
        Assert.assertTrue(feature.isOn());

        contextApi.updateProductContext(instanceId, true, createJSONParam("{\"vehicle\":{\"position\":1}}"));
        actionApi.calculate(instanceId, true);
        feature = featureApi.getFeatureByName(instanceId, "ui.Theme");
        Assert.assertTrue(!feature.isOn());

        Assert.assertNotEquals(0, Long.parseLong(actionApi.getLastCalculate(instanceId)));
    }


    @Test
    public void getCalcTimeBeforeFirstTimeTest() throws ApiException {
        String instanceId = product.getInstanceId();
        Assert.assertNotNull(instanceId);
        Assert.assertEquals(0, Long.parseLong(actionApi.getLastCalculate(instanceId)));
    }


    @Test
    public void pullTimeAfterPull() throws ApiException {
        String instanceId = product.getInstanceId();
        Assert.assertNotNull(instanceId);

        actionApi.pull(instanceId, Locale.getDefault().toString());
        Assert.assertNotEquals(0, Long.parseLong(actionApi.getLastPull(instanceId)));
    }


    @Test
    public void deletUserGroup() throws ApiException {
        String instanceId = product.getInstanceId();

        //set user group
        userGroupsApi.updateUserGroupsToProduct(instanceId, Arrays.asList(new String[]{"SampleAppDEV"}));
        actionApi.pull(instanceId, Locale.getDefault().toString());
        contextApi.updateProductContext(instanceId, true, createJSONParam("{\"vehicle\":{\"speed\":20}}"));
        actionApi.calculate(instanceId, true);


        Feature feature = featureApi.getFeatureByName(instanceId, "ui.Tabs");
        Assert.assertTrue(feature.isOn());

        userGroupsApi.updateUserGroupsToProduct(instanceId, Arrays.asList(new String[]{}));
        actionApi.calculate(instanceId, true);

        Assert.assertTrue(!featureApi.getFeatureByName(instanceId, "ui.Tabs").isOn());
    }


    private static String getDefaults() {
        File deafults_file = new File(ClassLoader.getSystemResource("swagger-client-defaults.json").getPath());
        try {
            return slurp(deafults_file);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        return null;
    }

    public static String slurp(final File file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            char[] buf = new char[1024];
            int r = 0;
            while ((r = reader.read(buf)) != -1) {
                result.append(buf, 0, r);
            }
        } finally {
            reader.close();
        }
        return result.toString();
    }
}


