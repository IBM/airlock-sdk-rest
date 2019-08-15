package com.ibm.airlock.rest.common;

import java.util.Hashtable;

public class Response {

    protected Object entity;
    protected int status;
    protected ContentType contentType;
    protected Hashtable<String, String> headers;

    Response(int status, Object entity, Hashtable<String, String> headers) {
        this.entity = entity;
        this.status = status;
        this.headers = headers;
    }

    public static ResponseBuilder status(int status) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.status(status);
        return responseBuilder;
    }

    public Object getEntity() {
        return entity;
    }

    public int getStatus() {
        return status;
    }

    public Hashtable<String, String> getHeaders() {
        return headers;
    }


    public static class ResponseBuilder {
        protected Object entity;
        protected int status;
        protected Hashtable<String, String> headers;


        public ResponseBuilder entity(Object entity) {
            this.entity = entity;
            return this;
        }

        public ResponseBuilder headers(Hashtable headers) {
            this.headers = headers;
            return this;
        }


        public ResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        public Response build() {
            return new Response(status, entity, headers);
        }
    }

    public enum ContentType {

        APPLICATION_JSON("application/json"),
        TEXT_PLAIN("text/plain"),
        TEXT_HTML("text/html");

        private String type;

        ContentType(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }
    }
}
