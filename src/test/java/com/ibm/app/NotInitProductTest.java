package com.ibm.app;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.ibm.app.services.BaseServiceTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class NotInitProductTest extends BaseServiceTest {

	public NotInitProductTest() {
		defaults = strJson;
		productId = getProductId();
	}

	//airlock.test.url
	private final static	 		String strJson = "{\n" +
			"	\"defaultLanguage\": \"en\",\n" + 
			"	\"devS3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\",\n" + 
			"	\"productId\": \"dd98a544-6991-4ba0-ae41-b227d91337fa\",\n" + 
			"	\"productName\": \"TestJavaProductNotInited\",\n" + 
			"	\"root\": {\n" + 
			"		\"features\": [\n" + 
			"			{\n" + 
			"				\"defaultConfiguration\": null,\n" + 
			"				\"defaultIfAirlockSystemIsDown\": false,\n" + 
			"				\"features\": [\n" + 
			"				],\n" + 
			"				\"name\": \"feature\",\n" + 
			"				\"namespace\": \"feature\",\n" + 
			"				\"noCachedResults\": false,\n" + 
			"				\"type\": \"FEATURE\",\n" + 
			"				\"uniqueId\": \"d58ad03f-0e7b-4887-803a-78c5b3364f56\"\n" + 
			"			}\n" + 
			"		],\n" + 
			"		\"type\": \"ROOT\",\n" + 
			"		\"uniqueId\": \"604dd08c-2ad9-4c95-8bb2-a54ed59fe953\"\n" + 
			"	},\n" + 
			"	\"s3Path\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/airlockdev\\/DEV4\\/\",\n" + 
			"	\"seasonId\": \"819cdb1b-f433-4f49-9f2a-b77c2302714a\",\n" + 
			"	\"supportedLanguages\": [\n" + 
			"		\"en\"\n" + 
			"	],\n" + 
			"	\"version\": \"V2.5\"\n" + 
			"}";


//	 	@Test
//	    public void testInit() {
//	        String response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/init").queryParam("appVersion", "9").request().put(Entity.json(strJson),String.class);
//	        Assert.assertTrue("Init Succeed".equals(response));
//	    }
	 	

	 	@Test
	    public void testNotInitCalculateAndSync() {
//	        String response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/init").queryParam("appVersion", "9").request().put(Entity.json(strJson),String.class);
//	        Assert.assertTrue("Init Succeed".equals(response));
	        Response response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/pull").request().post(Entity.json("{}"));
	        Assert.assertTrue(response.getStatus() == 404);
	        Response response2 = client.target(serverUrl + "/airlock/TestJavaProductNotInited/pull").request().get();
	        Assert.assertTrue(response2.getStatus() == 404);
//	        Assert.assertTrue(lastModifiedPull.after(now));
	        response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/calculate").request().post(Entity.json("{}"));
	        Assert.assertTrue(response.getStatus() == 404);
	         response2 = client.target(serverUrl + "/airlock/TestJavaProductNotInited/calculate").request().get();
	         Assert.assertTrue(response2.getStatus() == 404);
	        response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/sync").request().post(Entity.json("{}"));
	        Assert.assertTrue(response.getStatus() == 404);
	         response2 = client.target(serverUrl + "/airlock/TestJavaProductNotInited/sync").request().get();
	         Assert.assertTrue(response2.getStatus() == 404);	        //sdkFeature
	        response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/features/feature.feature").request().get();
	        Assert.assertTrue(response.getStatus() == 404);
	        response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/features/feature.feature/children").request().get();
	        Assert.assertTrue(response.getStatus() == 404);
	    }
	 	
	 	@Test
	    public void testNotInitAddRemoveProductContextField() {
//	        String responseStr = client.target(serverUrl + "/airlock/TestJavaProductNotInited/init").queryParam("appVersion", "9").request().put(Entity.json(strJson),String.class);
	        Response response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/context/newProdFieldName").request().put(Entity.text("value"));	        
	        Assert.assertTrue(response.getStatus() == 404);
	        response = client.target(serverUrl + "/airlock/TestJavaProductNotInited/context/newProdFieldName").request().delete();	        
	        Assert.assertTrue(response.getStatus() == 404);
	 	}
	 	
}
