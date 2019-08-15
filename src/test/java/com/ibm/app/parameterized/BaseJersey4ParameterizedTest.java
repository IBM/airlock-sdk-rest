package com.ibm.app.parameterized;

import com.ibm.app.services.BaseServiceTest;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Before;

public class BaseJersey4ParameterizedTest  extends BaseServiceTest {
    private static TestContainerFactory grizzlyWebTestContainerFactory;
    private static boolean serverIsStarted;

    @Before
    @Override
    public void setUp() throws Exception {
        if (!isExternal) {
            super.setUp();
        }else{
            if(!serverIsStarted){
                super.setUp();
                serverIsStarted = true;
            }
        }
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        if (isExternal) {
            if(grizzlyWebTestContainerFactory == null) {
                grizzlyWebTestContainerFactory = super.getTestContainerFactory();
            }
        } else {
            if(grizzlyWebTestContainerFactory == null) {
                grizzlyWebTestContainerFactory = new GrizzlyWebTestContainerFactory();
            }
        }
        return grizzlyWebTestContainerFactory;
    }
}
