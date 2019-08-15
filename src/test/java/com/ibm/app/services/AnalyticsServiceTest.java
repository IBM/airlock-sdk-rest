package com.ibm.app.services;

import com.ibm.airlock.rest.model.AnalyticsData;
import com.ibm.airlock.rest.model.Attribute;
import com.ibm.airlock.rest.model.FeatureAnalytics;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;



public class AnalyticsServiceTest extends BaseServiceTest {

    private final static String strJson = "{\n" +
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

//    private final static String strJson = "{\n"+
//            "\t\"defaultLanguage\": \"en\",\n"+
//            "\t\"devS3Path\": \"https:\\/\\/airlockstorage.blob.core.windows.net\\/airlockdev\\/\",\n"+
//            "\t\"productId\": \"aabe959b-c0b8-4920-a51f-4a004bf6d4ce\",\n"+
//            "\t\"productName\": \"Sample Car Phone App\",\n"+
//            "\t\"root\": {},\n"+
//            "\t\"s3Path\": \"https:\\/\\/airlockstorage.blob.core.windows.net\\/airlockdev\",\n"+
//            "\t\"seasonId\": \"3f0c0c14-7566-42ad-b9c1-f8271503e576\",\n"+
//            "\t\"supportedLanguages\": [\n"+
//            "\t\t\"en\",\n"+
//            "\t\t\"de\"\n"+
//            "\t],\n"+
//            "\t\"version\": \"V2.5\"\n"+
//            "}";


    public AnalyticsServiceTest() {
        defaults = strJson;
        productId = getProductId();
    }
    @Before
    public void pullAndCalculate() {
        try {
            Response response = client.target(serverUrl + "/products/" + productInstanceId + "/usergroups").request().put(Entity.json("[\"Adina\"]"));
            Assert.assertEquals(200, response.getStatus());
            response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("productId",productId).queryParam("locale","en_US").request().put(Entity.entity(productId, MediaType.TEXT_PLAIN_TYPE));
            Assert.assertEquals(200, response.getStatus());
            JSONObject sharedContext = new JSONObject();
            sharedContext.put("app",new JSONObject("{\"campaign\":\"value2\"}" ));

            putContext("/products/context",  sharedContext.toString(), true);
            JSONObject productContext = new JSONObject();
            productContext.put("profile",new JSONObject("{\"userId\":\"useridXXX\"}" ));
            putContext("/products/" + productInstanceId + "/context", productContext.toString(), true);

            response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync",true).queryParam("productId",productId).
                    request().put(Entity.json("{\"device\": {\"screenWidth\": 8 }}"));
            Assert.assertEquals(200, response.getStatus());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getAllFieldsForAnalytics() {
        try {
            AnalyticsData data = client.target(serverUrl + "/products/" + productInstanceId + "/analytics").request().get(AnalyticsData.class);
            Attribute[] contextAttributes = data.getContext();
            int contextAttributesexpectedCorrect = 0;
            for (Attribute attr: contextAttributes){
                if (attr.getKey().equals("profile.userId") && attr.getValue().equals("useridXXX")){
                    contextAttributesexpectedCorrect++;
                }
                if (attr.getKey().equals("app.campaign") && attr.getValue().equals("value2")){
                    contextAttributesexpectedCorrect++;
                }
            }

            Assert.assertTrue (contextAttributesexpectedCorrect == 2);
            FeatureAnalytics[] featureAnalytics = data.getFeatures();
            Assert.assertTrue (featureAnalytics.length >= 4);
            for (FeatureAnalytics feature: featureAnalytics ){
                if (feature.getFeatureName().equals("feature.featureBasedOnUserGroup")){
                    Assert.assertTrue(Arrays.toString(feature.getAppliedOrderingRules()).equals("[feature.oredering2]"));
                    String[] orderedChildren = feature.getOrderedChildren();
                    int expectedExists = 0;
                    for (String child: orderedChildren){
                        if (child.equals("feature.sub4") || child.equals("feature.sub3")){
                            expectedExists++;
                        }
                    }
                    Assert.assertTrue(expectedExists>=2);
                }
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getFeatureAttributesForAnalytics() {
        try {
            FeatureAnalytics featureAnalytics  = client.target(serverUrl + "/products/" + productInstanceId + "/analytics/features/f.subSub").request().get(FeatureAnalytics.class);
            Assert.assertTrue(featureAnalytics.getFeatureName().equals("f.subSub"));
            if (featureAnalytics!= null && featureAnalytics.getAttributes() != null && featureAnalytics.getAttributes().length == 1){
                Attribute attr = featureAnalytics.getAttributes()[0];
                Assert.assertTrue(attr.getKey().equals("aa") && attr.getValue().equals("bb"));
            }else{
                Assert.fail("missing expected \"aa\" attribute");
            }

            Response response  = client.target(serverUrl + "/products/" + productInstanceId + "/analytics/features/f.doNotExist").request().get();
            Assert.assertTrue(response.getStatus() == 404);


        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private void putContext(String path, String context, boolean clearPreviousContext) {
        Response response = client.target(serverUrl + path).
                queryParam("clearPreviousContext", clearPreviousContext).request().
                put(Entity.entity(context, MediaType.APPLICATION_JSON), Response.class);
        Assert.assertEquals(200, response.getStatus());
    }
}