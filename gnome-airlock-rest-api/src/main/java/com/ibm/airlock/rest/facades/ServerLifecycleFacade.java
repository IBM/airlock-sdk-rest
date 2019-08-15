package com.ibm.airlock.rest.facades;

import com.ibm.airlock.rest.common.Response;
import com.ibm.airlock.sdk.AirlockMultiProductsManager;

import java.io.IOException;

public class ServerLifecycleFacade {

    public static Response preStop() {
        try{
            AirlockMultiProductsManager.getInstance().copyCacheToSSD();
        }catch (IOException e){
            return Response.status(404).entity(e.getMessage()).build();
        }catch (Exception e1){
            return Response.status(500).entity("Fatal error happened while copy airlock cache to SSD drive,"+e1.getMessage()).build();
        }
        return Response.status(200).entity("Airlock cache was successfully copied to SSD drive").build();
    }

    public static Response liveness() {
        return Response.status(200).entity("").build();
    }

    public static Response postStart() {
        return Response.status(200).entity("").build();
    }

    public static Response readiness() {
        return Response.status(200).entity("").build();
    }
}
