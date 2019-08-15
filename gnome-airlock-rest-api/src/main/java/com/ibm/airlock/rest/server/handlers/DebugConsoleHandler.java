package com.ibm.airlock.rest.server.handlers;

import com.ibm.airlock.rest.util.ZipUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class DebugConsoleHandler implements HttpHandler {

    private final String pathToRoot;


    public DebugConsoleHandler(String pathToRoot, String compressedWebContent) throws IOException {

        this.pathToRoot = pathToRoot.endsWith(File.separator) ? pathToRoot : pathToRoot + File.separator;
        extractCompressedWebContent(pathToRoot, compressedWebContent);

        File[] files = new File(pathToRoot).listFiles();
        if (files == null)
            throw new IllegalStateException("Couldn't find web-root: " + pathToRoot);

    }

    private static class Asset {
        final byte[] data;

        Asset(byte[] data) {
            this.data = data;
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        String path = httpExchange.getRequestURI().getPath();
        try {
            path = path.substring(1);
            path = path.replaceAll("//", "/");
            if (path.length() == 0 || path.equals("debug-console"))
                path = "index.html";

            boolean fromFile = new File(pathToRoot + path).exists();
            InputStream in = fromFile ? new FileInputStream(pathToRoot + path)
                    : ClassLoader.getSystemClassLoader().getResourceAsStream(pathToRoot + path);
            Asset res = new Asset(readResource(in));

            if (path.endsWith(".js"))
                httpExchange.getResponseHeaders().set("Content-Type", "text/javascript");
            else if (path.endsWith(".html"))
                httpExchange.getResponseHeaders().set("Content-Type", "text/html");
            else if (path.endsWith(".css"))
                httpExchange.getResponseHeaders().set("Content-Type", "text/css");
            else if (path.endsWith(".json"))
                httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            else if (path.endsWith(".svg"))
                httpExchange.getResponseHeaders().set("Content-Type", "image/svg+xml");
            if (httpExchange.getRequestMethod().equals("HEAD")) {
                httpExchange.getResponseHeaders().set("Content-Length", "" + res.data.length);
                httpExchange.sendResponseHeaders(200, -1);
                return;
            }

            httpExchange.sendResponseHeaders(200, res.data.length);
            httpExchange.getResponseBody().write(res.data);
            httpExchange.getResponseBody().close();
        } catch (Throwable t) {
            sendNoFoundServiceResponse(httpExchange);
        }
    }

    private void sendNoFoundServiceResponse(HttpExchange httpExchange){
        BaseHandler.sentServerError(httpExchange, 404, "Bad request: there is not rest service matching given request\n");
        System.err.println("Bad request: there is not rest service matching given request: " + httpExchange.getRequestURI().getPath());
    }

    private static void extractCompressedWebContent(String destDirectoryPath, String compressedZipFilePath) throws IOException {
        ZipUtils.unZip(compressedZipFilePath, destDirectoryPath);
    }

    private static byte[] readResource(InputStream in) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        OutputStream gout = new DataOutputStream(bout);
        byte[] tmp = new byte[4096];
        int r;
        while ((r = in.read(tmp)) >= 0)
            gout.write(tmp, 0, r);
        gout.flush();
        gout.close();
        in.close();
        return bout.toByteArray();
    }
}
