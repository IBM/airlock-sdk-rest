package com.ibm.airlock.rest.server;

import com.ibm.airlock.rest.server.handlers.Handler;
import com.ibm.airlock.rest.server.handlers.ProductActionsHandler;
import com.ibm.airlock.rest.server.handlers.ProductHandler;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RestUrlDispacherTest {

    private String CONTEXT_PREFIX = "/airlock/api";

    HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
    Handler poduct = Mockito.mock(ProductHandler.class);
    Handler poductActions = Mockito.mock(ProductActionsHandler.class);
    URI url = Mockito.mock(URI.class);
    private static String resultClazz = null;
    private String urlPath;
    private String needClazz;
    RestUrlDispatcher restUrlDispacher;


    public RestUrlDispacherTest(String urlPath, Class needClazz) {
        this.urlPath = CONTEXT_PREFIX + urlPath;
        this.needClazz = needClazz.getName();
    }

    @Before
    public void setUp() {
        when(url.getPath()).thenReturn(this.urlPath);
        when(httpExchange.getRequestURI()).thenReturn(url);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                resultClazz = ProductActionsHandler.class.getName();
                return null;
            }
        }).when(poductActions).handle(any());

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                resultClazz = ProductHandler.class.getName();
                return null;
            }
        }).when(poduct).handle(any());

        RestUrlDispatcher.setProductActionHandler(poductActions);
        RestUrlDispatcher.setProductHandler(poduct);
        restUrlDispacher = new RestUrlDispatcher();
    }

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection getUrlPaths() {
        Object[][] urlPaths = new Object[][]{
                //product
                {"/products/init?appVersion=1.0.0&encryptionKey=ASAADADSDSD", ProductHandler.class},
                {"/products/1212-1212-1212-121212", ProductHandler.class},

                //actions
                {"/products/1212-1212-1212-121212/pull", ProductActionsHandler.class},
                {"/products/1212-1212-1212-121212/calculate", ProductActionsHandler.class},
                {"/products/1212-1212-1212-121212/sync", ProductActionsHandler.class},
                {"/products/pullAll", ProductActionsHandler.class},
        };
        return Arrays.asList(urlPaths);
    }


    @Test
    public void handle() {
        restUrlDispacher.handle(httpExchange);
        Assert.assertNotNull(resultClazz);
        Assert.assertEquals(needClazz, resultClazz);
    }
}