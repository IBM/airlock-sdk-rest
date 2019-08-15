package com.ibm.app.parameterized;


import com.ibm.app.services.AirlockProduct;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.ws.rs.core.Application;
import java.util.Collection;
import java.util.LinkedList;


@RunWith(Parallelized.class)
public class ParameterizedContextServiceTest  extends BaseJersey4ParameterizedTest {

    private final String productName;

    public ParameterizedContextServiceTest(String productName) {
        // the productName will be taken from getProducts method in the runtime.
        this.productName = productName;
    }

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection getProducts() {
        // set the number of different products
        int productsNumber = 3;
        LinkedList<String> products = new LinkedList<>();
        for (int i = 0; i < productsNumber; i++) {
            // auto-generated id for each Product
            products.add(java.util.UUID.randomUUID().toString());
        }
        return products;
    }

    private static final String serverUrl = getServerUrl();

    @Override
    protected Application configure() {
        return new ResourceConfig(AirlockProduct.class);
    }
}
