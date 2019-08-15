package com.ibm.app.services;


import com.ibm.airlock.rest.model.Product;
import com.ibm.airlock.rest.server.GnomeAirlockRest;
import com.ibm.app.AppServletContextListener;
import com.ibm.app.EmptyServletContextListener;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Locale;

public class BaseServiceTest extends JerseyTest {

    protected String productId;
    protected String productInstanceId;
    protected String encryptionKey;
    protected String defaults;
    protected static boolean isExternal;
    protected static String appVersion = "9.0";

    protected static Client client = ClientBuilder.newClient();
    protected static String serverUrl;
    protected static boolean gnoneIsRunning;


    public BaseServiceTest() {
        serverUrl = getServerUrl();
        if (System.getenv("airlock.test.external.url") != null) {
            if (System.getenv("airlock.test.external.url").equals("gnome")) {
                try {
                    if (!gnoneIsRunning) {
                        GnomeAirlockRest.main(new String[]{});
                        gnoneIsRunning = true;
                    }
                } catch (Exception e) {
                    Assert.fail(e.getMessage());
                }
            }
        }
    }


    //executed before each test
    @Before
    public void resetAndInit() {
        Locale.setDefault(new Locale("en", "US"));
        resetAllProducts();
        try {
            Product newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", appVersion).queryParam("encryptionKey", encryptionKey)
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

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {
        if (System.getenv("airlock.test.external.url") != null) {
            return ServletDeploymentContext.forPackages(
                    "com.ibm.app,com.ibm.app.services.debug").addListener(EmptyServletContextListener.class).build();
        } else {
            return ServletDeploymentContext.forPackages(
                    "com.ibm.app,com.ibm.app.services.debug").addListener(AppServletContextListener.class).build();
        }
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(AirlockProduct.class);
    }

    protected static String getServerUrl() {
        String url = null;
        if (System.getenv("airlock.test.external.url") != null) {
            isExternal = true;
            if (System.getenv("airlock.test.external.url").equals("gnome")) {
                url = "http://localhost:8080/airlock/api";
            } else {
                url = System.getenv("airlock.test.external.url");
            }
        }

        if (url == null) {
            //Grizzly default url
            url = "http://localhost:9998";
        }
        return url;
    }

    protected void resetAllProducts() {

        //delete shared context
        deleteContext("/products/context");

        Product[] products = client.target(serverUrl + "/products/").request().get(Product[].class);
        for (Product product : products) {
            try {
                Response response = client.target(serverUrl + "/products/" + product.getInstanceId()).request().delete();
                Assert.assertEquals(response.getStatus()+"",((response.getStatus()) == 200 || response.getStatus() == 404),true);
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }
        }
        products = client.target(serverUrl + "/products/").request().get(products.getClass());
        //Assert.assertEquals(products.length, 0);
    }

    protected String getDefaultFileName() {
        return "";
    }

    protected String getDefaults() {
        try {
            return readFile(ClassLoader.getSystemResource(getDefaultFileName()).getPath());
        } catch (IOException | NullPointerException e) {
            Assert.fail(e.getMessage());
        }
        return null;
    }

    protected static String readFile(String filePath) throws IOException {

        File file = new File(filePath);
        InputStream inStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sBuilder = new StringBuilder();
        String strLine;
        while ((strLine = br.readLine()) != null) {
            sBuilder.append(strLine);
        }
        br.close();
        return sBuilder.toString();
    }

    protected void deleteContext(String path) {
        Response response = client.target(serverUrl + path).request().delete();
        Assert.assertEquals(200, response.getStatus());
    }

    protected String getProductId() {
        if (productId == null || productId.isEmpty()) {
            return new JSONObject(defaults).optString("productId");
        } else {
            return this.productId;
        }
    }

}
