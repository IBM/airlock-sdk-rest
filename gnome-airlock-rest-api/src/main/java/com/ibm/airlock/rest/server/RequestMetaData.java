package com.ibm.airlock.rest.server;

import com.ibm.airlock.rest.server.handlers.Handler;

import java.util.Hashtable;
import java.util.regex.Pattern;

public class RequestMetaData {

    private String urlPattern;
    private Pattern pattern;
    private Handler handler;


    RequestMetaData(String urlPattern, Handler handler) {
        this.urlPattern = RestUrlDispatcher.CONTEXT_PREFIX+urlPattern;
        this.handler = handler;
        this.pattern =  Pattern.compile(this.urlPattern);
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public Handler getHandler() {
        return handler;
    }

    public boolean matchHandler(String url) {
        return pattern.matcher(url).matches();
    }

    public enum Method {
        POST,
        GET,
        PUT,
        DELETE,
        OPTIONS,
        HEAD
    }
}
