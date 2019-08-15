package com.ibm.app;

import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.ibm.app.services.BaseServiceTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class SimpleTest extends BaseServiceTest {

	public SimpleTest() {
		defaults = strJson;
		productId = getProductId();
	}

    @Before
	@Ignore
    public void resetProduct() {
        try {
            Response response = client.target(serverUrl + "/airlock/debug/" + productId ).request().put(Entity.json("{}"));
            Assert.assertTrue(((response.getStatus()) == 200 || response.getStatus() == 404));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        try {
            String response = client.target(serverUrl + "/airlock/" + productId + "/init").queryParam("appVersion", "9").request().put(Entity.json(strJson), String.class);
            Assert.assertTrue("Init Succeed".equals(response));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        //executed before each test
    }
	//airlock.test.url
	private final static	 		String strJson = "{\n" + 
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



	 	@Test
		@Ignore
	    public void testPull() {
//	        String response = client.target(serverUrl + "/airlock/TestJavaSDK/init").queryParam("appVersion", "9").request().put(Entity.json(strJson),String.class);
//	        Assert.assertTrue("Init Succeed".equals(response));
	        String response = client.target(serverUrl + "/airlock/TestJavaSDK/pull").request().post(Entity.json("{}"),String.class);
	        Assert.assertTrue("Pull Succeed".equals(response));
	    }	
	 	
	 	@Test
		@Ignore
	    public void testGetStringByKey() {
	    	//validate pull before
	        String response = client.target(serverUrl + "/airlock/TestJavaSDK/pull").request().post(Entity.json("{}"),String.class);
	        Assert.assertTrue("Pull Succeed".equals(response));
	        response = client.target(serverUrl + "/airlock/TestJavaSDK/strings/strKey").request().get(String.class);
	        Assert.assertTrue("strValue".equals(response));
	    }
	 	
	 	@Test
		@Ignore
	    public void testAddRemoveSharedContextField() {
	        Response response = client.target(serverUrl + "/airlock/context/newFieldName").request().put(Entity.text("value"));	        
	        Assert.assertTrue(response.getStatus() == 200);
	        response = client.target(serverUrl + "/airlock/context/newFieldName").request().delete();	        
	        Assert.assertTrue(response.getStatus() == 200);
	 	}
	 	
	 	@Test
		@Ignore
	    public void testAddRemoveProductContextField() {
//	        String responseStr = client.target(serverUrl + "/airlock/TestJavaSDK/init").queryParam("appVersion", "9").request().put(Entity.json(strJson),String.class);
	        Response response = client.target(serverUrl + "/airlock/TestJavaSDK/context/newProdFieldName").request().put(Entity.text("value"));	        
	        Assert.assertTrue(response.getStatus() == 200);
	        response = client.target(serverUrl + "/airlock/TestJavaSDK/context/newProdFieldName").request().delete();	        
	        Assert.assertTrue(response.getStatus() == 200);
	 	}
	 	@Test
		@Ignore
	    public void testAlreadyInit() {
//	        Response response = client.target(serverUrl + "/airlock/TestJavaSDK/init").queryParam("appVersion", "9").request().put(Entity.json(strJson));
//	        Assert.assertTrue(response.getStatus() == 200);
	 		Response response = client.target(serverUrl + "/airlock/TestJavaSDK/init").queryParam("appVersion", "9").request().put(Entity.json(strJson));
	        Assert.assertTrue(response.getStatus() == 304);
	 	}
	 	
}
