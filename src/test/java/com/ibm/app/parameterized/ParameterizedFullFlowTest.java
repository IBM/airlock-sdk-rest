package com.ibm.app.parameterized;

import com.ibm.airlock.rest.model.Feature;
import com.ibm.airlock.rest.model.Product;
import com.ibm.app.services.AirlockProduct;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

// Parallelized is used to run parameterised tests in parallel
@RunWith(Parallelized.class)
public class ParameterizedFullFlowTest extends BaseJersey4ParameterizedTest {
    private final String productId;
    private final String defaultsFileName;



    public ParameterizedFullFlowTest(String productId, String defaultsFileName) {
        // the productId will be taken from getProducts method in the runtime.
        this.productId = productId;
        this.defaultsFileName = defaultsFileName;

    }

    @Parameterized.Parameters(name = "{index}:{1}")
    public static Collection getProducts() {
        // set the number of different products
        LinkedList<String> products = new LinkedList<>();
        return Arrays.asList(new Object[][]{
                {"cdd52d55-df5d-4375-ac41-1086e4f1c7a3","TestJavaSDK"},
                {"67ac3306-0f75-4d9a-8434-f72771bcb670","TestJavaSDK2"}
        });
    }

    @Override
    @Before
    public void resetAndInit() {
        try {
            Response response = client.target(serverUrl + "/products/" + productId + "/usergroups").request().put(Entity.json("[]"));
            Assert.assertTrue(response.getStatus() == 200 || response.getStatus() == 400);

            Response resetSharedContext = client.target(serverUrl + "/products/context").request().delete();
            Assert.assertEquals(200, resetSharedContext.getStatus());

            response = client.target(serverUrl + "/products/context").request().delete();
            Assert.assertEquals(200, response.getStatus());

            response = client.target(serverUrl + "/products/" + productId).request().delete();
            Assert.assertTrue(((response.getStatus()) == 200 || response.getStatus() == 404));

            Product newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", "9").request().post(Entity.json(getDefaults()), Product.class);
            this.productInstanceId = newProduct.getInstanceId();
            Assert.assertNotNull(newProduct);

            response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"Adina\"]"));
            Assert.assertEquals(200, response.getStatus());
            response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("productId", productId).queryParam("locale", "en_US").request().put(Entity.entity(productId, MediaType.TEXT_PLAIN_TYPE));
            Assert.assertEquals(200, response.getStatus());
            response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).queryParam("productId", productId).request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
            Assert.assertEquals(200, response.getStatus());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        //executed before each test
    }

    @Override
    public String getDefaultFileName(){
        return defaultsFileName + ".json";
    }

    private static final String serverUrl = getServerUrl();

    @Override
    protected Application configure() {
        return new ResourceConfig(AirlockProduct.class);
    }


    @Test
    public void testCalculateBasedOnProductContext() {

        Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/f.featureBasedOnProductContext").request().get(Feature.class);
        Assert.assertTrue(feature != null);
        Assert.assertTrue(!feature.isOn());
        Assert.assertTrue(feature.getDebugInfo().getSource() == com.ibm.airlock.common.data.Feature.Source.SERVER);

        String flavor = "falvour";
        char appSuffix = defaultsFileName.substring(defaultsFileName.length()-1).charAt(0);

        if (Character.isDigit(appSuffix)){
            flavor += appSuffix;
        }else{
            flavor += "1";
        }

        JSONObject context = new JSONObject("{\"device\":{\"appFlavor\":"+ flavor+"}}");

        Response response = client.target(serverUrl + "/products/"+ productInstanceId+"/context").request().put(Entity.entity( context.toString(), MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());

        //calculate that feature should be on based on context
        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json("{\"device\": {\"screenWidth\": 18 }}"));
        Assert.assertEquals(200, response.getStatus());

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/f.featureBasedOnProductContext").request().get(Feature.class);
        Assert.assertTrue(feature != null);
        Assert.assertTrue(feature.isOn());
        Assert.assertTrue(feature.getDebugInfo().getSource() == com.ibm.airlock.common.data.Feature.Source.SERVER);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response = client.target(serverUrl + "/products/"+ productInstanceId+"/context").request().delete();
        Assert.assertEquals(200, response.getStatus());

        //calculate that feature should be on based on context
        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json("{\"device\": {\"screenWidth\": 18 }}"));
        Assert.assertEquals(200, response.getStatus());

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/f.featureBasedOnProductContext").request().get(Feature.class);
        Assert.assertTrue(feature != null);
        Assert.assertTrue(!feature.isOn());
        Assert.assertTrue(feature.getDebugInfo().getSource() == com.ibm.airlock.common.data.Feature.Source.SERVER);

    }

    @Test
    public void testCalculateBasedOnSharedContext() {
        Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/feature.featueOnBasedContexed").request().get(Feature.class);
        Assert.assertTrue(feature != null);
        Assert.assertTrue(!feature.isOn());
        Assert.assertTrue(feature.getDebugInfo().getSource() == com.ibm.airlock.common.data.Feature.Source.SERVER);

        int width = 12;
        char appSuffix = defaultsFileName.substring(defaultsFileName.length()-1).charAt(0);

        if (Character.isDigit(appSuffix)){
            width = 20;
        }

        JSONObject context = new JSONObject("{\"device\":{\"screenWidth\":"+ width+"}}");

        Response response = client.target(serverUrl + "/products/context").request().put(Entity.entity( context.toString(), MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());

        //calculate that feature should be on based on context
        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json("{\"device\": {\"screenWidth\": 18 }}"));
        Assert.assertEquals(200, response.getStatus());

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/feature.featueOnBasedContexed").request().get(Feature.class);
        Assert.assertTrue(feature != null);
        Assert.assertTrue(feature.isOn());
        Assert.assertTrue(feature.getDebugInfo().getSource() == com.ibm.airlock.common.data.Feature.Source.SERVER);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response = client.target(serverUrl + "/products/context").request().delete();
        Assert.assertEquals(200, response.getStatus());

        //calculate that feature should be on based on context
        response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                request().put(Entity.json("{\"device\": {\"screenWidth\": 18 }}"));
        Assert.assertEquals(200, response.getStatus());

        feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/feature.featueOnBasedContexed").request().get(Feature.class);
        Assert.assertTrue(feature != null);
        Assert.assertTrue(!feature.isOn());
        Assert.assertTrue(feature.getDebugInfo().getSource() == com.ibm.airlock.common.data.Feature.Source.SERVER);
    }
}
