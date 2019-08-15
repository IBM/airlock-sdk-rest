package com.ibm.airlock.rest.server.handlers;

import com.google.gson.Gson;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.rest.server.RequestMetaData;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;
import java.util.Hashtable;
import java.util.Optional;
import java.util.logging.Logger;

public class BaseHandler implements Handler {

    private static final Logger logger = Logger.getLogger(BaseHandler.class.toString());

    private static final String AND_DELIMITER = "&";
    private static final String EQUAL_DELIMITER = "=";

    protected RequestMetaData.Method method;
    protected Hashtable<String, String> queryParameters;
    protected String[] pathParameters;
    protected String body;

    @Override
    public void handle(HttpExchange request) {
        queryParameters = parseQueryParams(request.getRequestURI());
        body = slurp(request.getRequestBody(), 1024);
        method = RequestMetaData.Method.valueOf(request.getRequestMethod());
        pathParameters = request.getRequestURI().getPath().split("/");

        if (method == RequestMetaData.Method.POST) {
            post(request);
        }
        if (method == RequestMetaData.Method.GET) {
            get(request);
        }

        if (method == RequestMetaData.Method.PUT) {
            put(request);
        }

        if (method == RequestMetaData.Method.DELETE) {
            delete(request);
        }

        if (method == RequestMetaData.Method.OPTIONS) {
            sendRespone(Response.status(200).entity("").build(), request,
                    Response.ContentType.TEXT_PLAIN);
        }
    }


    private void notSupportedMethod(HttpExchange request) {
        sendRespone(Response.status(405).
                        entity("HTTP Method [" + request.getRequestMethod() + "] not supported for:[" + request.getRequestURI() + "]").
                        build(), request,
                Response.ContentType.TEXT_PLAIN);
    }

    @Override
    public void post(HttpExchange request) {
        notSupportedMethod(request);
    }

    @Override
    public void get(HttpExchange request) {
        notSupportedMethod(request);
    }

    @Override
    public void put(HttpExchange request) {
        notSupportedMethod(request);
    }

    @Override
    public void delete(HttpExchange request) {
        notSupportedMethod(request);
    }


    public static void sentServerError(HttpExchange request, int code, String error) {
        try {
            request.getResponseHeaders().set("Content-Type", Response.ContentType.TEXT_PLAIN.type());
            request.sendResponseHeaders(code, error.length());
            OutputStream os = request.getResponseBody();
            os.write(error.getBytes());
            os.close();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    protected static void sendNotFoundServiceError(HttpExchange request) {
        BaseHandler.sentServerError(request, 404, "Bad request: there is not rest service matching given request");
    }


    protected void sendRespone(Response response, HttpExchange request, Response.ContentType type) {
        try {
            request.getResponseHeaders().set("Content-Type", type.type());

            String responseBody;
            if (response.getEntity() instanceof String) {
                responseBody = response.getEntity().toString();
            } else {
                responseBody = new Gson().toJson(response.getEntity());
            }

            responseBody = responseBody == null ? "" : responseBody;
            request.sendResponseHeaders(response.getStatus(), responseBody.length());
            OutputStream os = request.getResponseBody();
            os.write(responseBody.getBytes());
            os.close();
        } catch (Exception e) {
            sentServerError(request, 505, e.getMessage());
        }
    }


    /**
     * Creates the response from query params.
     *
     * @param uri the uri
     * @return the string
     */
    private Hashtable<String, String> parseQueryParams(URI uri) {

        Hashtable<String, String> arguments = new Hashtable<>();
        String query = uri.getQuery();
        if (query != null) {
            String[] queryParams = query.split(AND_DELIMITER);
            if (queryParams.length > 0) {
                for (String parameter : queryParams) {
                    if (parameter.split(EQUAL_DELIMITER).length > 1) {
                        arguments.put(parameter.split(EQUAL_DELIMITER)[0],
                                parameter.split(EQUAL_DELIMITER)[1]);
                    } else {
                        arguments.put(parameter.split(EQUAL_DELIMITER)[0], "");
                    }
                }
            }
        }

        return arguments;
    }

    protected Optional<String> getPathParameter(int index) {
        if (pathParameters.length > index) {
            return Optional.of(pathParameters[index]);
        }
        return Optional.empty();
    }

    public static String slurp(final InputStream is, final int bufferSize) {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        } catch (UnsupportedEncodingException ex) {
            /* ... */
        } catch (IOException ex) {
            /* ... */
        }
        return out.toString();
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
}
