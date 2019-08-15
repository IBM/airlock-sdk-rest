package com.ibm.airlock.rest.facades;

import com.ibm.airlock.common.AirlockCallback;
import com.ibm.airlock.common.AirlockProductManager;
import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserGroupsFacade {
    private static final Logger logger = Logger.getLogger(UserGroupsFacade.class.toString());


    public static Response getUserGroups(String productInstanceId) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            List<String> deviceUserGroups = airlockProductManager.getDeviceUserGroups();
            if (deviceUserGroups == null) {
                return Response.status(404).build();
            }
            return Response.status(200).entity(deviceUserGroups).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get user groups failed").build();
        } catch (Throwable th) {
            return Response.status(500).entity("Get user groups failed").build();
        }
    }


    public static Response updateUserGroupsToProduct(String productInstanceId, List<String> userGroups) {
        try {
            AirlockProductManager airlockProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }

            boolean setUserGroupShouldBeSkipped = true;

            // check if the current product user groups list is different than the new one.
            List<String> currentUserGroups = airlockProductManager.getDeviceUserGroups();

            if (currentUserGroups.size() != userGroups.size()) {
                setUserGroupShouldBeSkipped = false;
            } else {
                for (String userGroup : currentUserGroups) {
                    boolean found = false;
                    for (int i = 0; i < userGroups.size(); i++) {
                        if (userGroups.get(i).equals(userGroup)) {
                            found = true;
                            continue;
                        }
                    }
                    if (!found) {
                        setUserGroupShouldBeSkipped = false;
                    }
                }
            }

            if (currentUserGroups.size() == 0 || !setUserGroupShouldBeSkipped) {
                airlockProductManager.setDeviceUserGroups(userGroups);
            }

            return Response.status(200).entity("").build();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Add user groups failed").build();
        }
    }

    public static Response getAllUserGroups(String productInstanceId) {
        try {

            AirlockProductManager airlockDebugProductManager = AirlockMultiProductsManager.getInstance().getAirlockProductManager(productInstanceId);
            if (airlockDebugProductManager == null) {
                return Response.status(400).entity("Product not initialized").build();
            }
            JSONArray userGroupsJson = getUserGroups(airlockDebugProductManager);

            List<String> userGroups = new ArrayList<>();

            if (userGroupsJson != null && userGroupsJson.length() > 0) {
                int length = userGroupsJson.length();
                for (int i = 0; i < length; i++) {
                    userGroups.add((String) userGroupsJson.get(i));
                }
            }
            return Response.status(200).entity(userGroups).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity("Get user groups failed").build();
        } catch (Throwable th) {
            return Response.status(500).entity("Get user groups failed").build();
        }
    }

    private static JSONArray getUserGroups(AirlockProductManager airlockDebugProductManager) throws InterruptedException, TimeoutException {
        final CountDownLatch latch = new CountDownLatch(1);
        final JSONArray userGroups = new JSONArray();
        airlockDebugProductManager.getServerUserGroups(new AirlockCallback() {
            public void onFailure(Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                latch.countDown();
            }

            public void onSuccess(String msg) {
                userGroups.put(new JSONArray(msg));
                latch.countDown();
            }
        });
        if (!latch.await(30, TimeUnit.SECONDS)) {
            throw new TimeoutException();
        } else {
            return userGroups.length() > 0 ? (JSONArray) userGroups.get(0) : userGroups;
        }
    }
}
