package com.ibm.airlock.rest.server.handlers;

import com.sun.net.httpserver.HttpExchange;

public interface Handler {
    public void handle(HttpExchange request);
    public void post(HttpExchange request);
    public void get(HttpExchange request);
    public void put(HttpExchange request);
    public void delete(HttpExchange request);
}
