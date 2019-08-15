package com.ibm.app.services;

import com.ibm.airlock.rest.model.Feature;
import io.apptik.json.JsonElement;
import io.apptik.json.generator.JsonGenerator;
import io.apptik.json.generator.JsonGeneratorConfig;
import io.apptik.json.schema.Schema;
import io.apptik.json.schema.SchemaV4;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.*;


public class AirlockStatelessCalcStressTest extends BaseServiceTest {


    private static final String DEFAULTS_NAME = "StatelessDefaults.json";
    private Boolean stopAllSenders = false;
    private int requestsCounter = 0;
    private int size = 2;
    private int index = 0;
    private String[] contexts = new String[size];

    protected String getDefaultFileName() {
        return DEFAULTS_NAME;
    }


    public AirlockStatelessCalcStressTest() {
        defaults = getDefaults();
        productId = getProductId();
        encryptionKey = null;
    }

//    @Test
    public void testStressCalculate() {
        Response response = client.target(serverUrl + "/products/" + productInstanceId + "/pull").queryParam("locale", "en_US").request().put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertEquals(response.getStatus(), 200);


        CountDownLatch doneAll = new CountDownLatch(1);
        ExecutorService threads = Executors.newFixedThreadPool(size);
        List<Callable<Boolean>> toRun = new ArrayList(size);
        for (index = 0; index < size; index++) {
            contexts[index] = generateContextData();
            if (index % 2 == 0) {
                contexts[index] = new JSONObject(contexts[index]).getJSONObject("device").
                        put("localeCountryCode", "US").toString();

            }
            System.out.println("Start thread " + index);
            toRun.add(new RequestSender(index));
        }

        // all tasks executed in different threads, at 'once'.
        try {
            List<Future<Boolean>> futures = threads.invokeAll(toRun);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        try {
            doneAll.await(10 * 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        // no more need for the threadpool
        threads.shutdown();
        stopAllSenders = true;
    }


    private class RequestSender implements Callable<Boolean> {

        private int index;

        RequestSender(int index) {
            this.index = index;
        }

        @Override
        public Boolean call() throws Exception {
            while (!stopAllSenders) {
                Response response = client.target(serverUrl + "/products/" + productInstanceId + "/calculate").queryParam("sync", true).
                        request().put(Entity.json(contexts[index]));
                Assert.assertEquals(200, response.getStatus());

                Feature feature = client.target(serverUrl + "/products/" + productInstanceId + "/features/root").request().get(Feature.class);
                Assert.assertTrue(feature != null);
//                Assert.assertTrue(feature.isOn());
                Assert.assertTrue(feature.getDebugInfo().getSource() == com.ibm.airlock.common.data.Feature.Source.SERVER);


                FeaturesMap map = new FeaturesMap(feature);
                requestsCounter++;
                if (requestsCounter % (size*4) == 0) {
                    System.out.println("So far were sent " + requestsCounter + " requests");
                    System.out.println("Features on: " + map.onFeatures);
                    System.out.println("Features off: " + map.offFeatures);
                    System.out.println("Features Airlock Control Over Modules: " + map.getFeature("modules.Airlock Control Over Modules").isOn());
                }
                int delta = (int) (Math.random() * 1000);
                try {
                    Thread.sleep(2000 - delta);
                } catch (InterruptedException e) {
                    Assert.fail(e.getMessage());
                }
            }
            return true;
        }
    }


    private String generateContextData() {
        try {
            String contextSchema = readFile(ClassLoader.getSystemResource("context_schema.json").getPath());
            Schema schema = new SchemaV4().wrap(JsonElement.readFrom(contextSchema).asJsonObject());

            JsonGeneratorConfig gConf = new JsonGeneratorConfig();

            return new JsonGenerator(schema, gConf).generate().asJsonObject().toString();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return "{}";
    }

    private class FeaturesMap {
        private Hashtable<String, Feature> features = new Hashtable<>();
        private int onFeatures = 0;
        private int offFeatures = 0;

        public FeaturesMap(Feature parent) {
            traverse(parent);
        }

        private void traverse(Feature parent) {
            if (parent != null) {
                features.put(parent.getName(), parent);
                if (parent.isOn()) {
                    onFeatures++;
                } else {
                    offFeatures++;
                }
            }
            if (parent.getChildren() != null && parent.getChildren().size() > 0) {
                for (Feature feature : parent.getChildren()) {
                    traverse(feature);
                }
            }
        }

        public Feature getFeature(String name) {
            return features.get(name);
        }

        public Hashtable<String, Feature> getFeatures() {
            return features;
        }

        public int getOnFeatures() {
            return onFeatures;
        }

        public int getOffFeatures() {
            return offFeatures;
        }
    }
}
