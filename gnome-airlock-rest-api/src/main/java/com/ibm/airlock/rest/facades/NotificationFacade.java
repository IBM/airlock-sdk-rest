package com.ibm.airlock.rest.facades;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import com.ibm.airlock.sdk.config.ConfigurationManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NotificationFacade {

    private static final Logger logger = Logger.getLogger(NotificationFacade.class.toString());

    private static final OkHttpClient client = new OkHttpClient();

    private static final String LOCATION_KEY = "Location";

    public static Response vasSubscriptions(String reqURI, String subscription) {

        String VASurl = NotificationFacade.buildVASURL();
        if (VASurl.isEmpty()) {
            return Response.status(500).entity("Unable to read VAS address from environment variable 'VAS_ADDRESS'").build();

        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), subscription);

        Request VASrequest = new Request.Builder()
                .url(VASurl)
                .post(body)
                .build();

        Response res = processRequest(VASrequest);
        Hashtable<String, String> headers = res.getHeaders();
        String oldLocation = headers.get(LOCATION_KEY);
        String newLocation;

        if (oldLocation != null) {
            int index = oldLocation.lastIndexOf('/');
            if (index > 0) {
                newLocation = (reqURI.endsWith("/")) ? reqURI + oldLocation.substring(index + 1) : reqURI + '/' + oldLocation.substring(index + 1);
                headers.replace(LOCATION_KEY, oldLocation, newLocation);
            }
        }
        return res;
    }

    public static Response deleteVasSubscriptions(String id) {

        String VASurl = NotificationFacade.buildVASURL();
        if (VASurl.isEmpty()) {
            return Response.status(500).entity("Unable to read VAS address from environment variable 'VAS_ADDRESS'").build();
        }
        VASurl += id;

        Request request = new Request.Builder()
                .url(VASurl)
                .delete()
                .build();

        return processRequest(request);
    }

    public static Response notificationHandler(List<Map<String, Object>> notificationsList) {
        String context = buildContext(notificationsList);
        try {
            AirlockMultiProductsManager.getInstance().updateSharedContext(context, false);
            return Response.status(200).entity("Shared context from notification was updated successfully").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Set shared context failed").build();
        }
    }

    private static String buildVASURL() {

        String vasAddress = ConfigurationManager.getVASAddress();
        if (vasAddress == null) {
            return "";
        }

        String vasVersion  = ConfigurationManager.getVASVersion();

        String VASurl = vasAddress;
        if (!VASurl.endsWith("/")) {
            VASurl += '/';
        }
        VASurl += vasVersion + "/vehicle/subscriptions/";
        return VASurl;
    }

    private static Response processRequest(Request request) {

        try {
            okhttp3.Response res = client.newCall(request).execute();

            // copy headers from VAS to caller response
            Hashtable<String, String> headers = new Hashtable();
            res.headers().names().forEach(headerName -> {
                if (res.header(headerName) != null) {
                    headers.put(headerName, res.header(headerName));
                }
            });

            return Response.status(res.code()).entity(res.body() == null ? "" : res.body().string()).headers(headers).build();
        } catch (IOException e) {
            return Response.status(500).entity(e).build();
        }
    }

    private static String buildContext(List<Map<String, Object>> notificationsList) {
        JSONObject out = new JSONObject();
        for (Map<String, Object> notification : notificationsList) {

            JSONObject item = new JSONObject(notification);
            out = buildNotificationContext(item, out);
        }
        return out.toString();
    }

    private static JSONObject buildNotificationContext(JSONObject notificationItem, JSONObject out) {

        String topic = notificationItem.getString("topic");
        String[] topicArr = topic.split("/");

        if (topicArr.length == 0) return out;

        JSONObject parent = out;
        for (int i = 0; i < topicArr.length - 1; i++) {
            String key = topicArr[i];

            if (key.isEmpty())
                continue;

            if (parent.has(key)) {
                parent = parent.getJSONObject(key);
                continue;
            }

            JSONObject child = new JSONObject();
            parent.put(key, child);
            parent = child;
        }

        String propKey = topicArr[topicArr.length - 1];
        parent.put(propKey, notificationItem.get("value"));
        return out;
    }
}
