package com.ibm.app.storage;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public interface CloudStorageDAO {
    public JSONObject readDataAsJSON(String path)  throws IOException, JSONException;
    public String readDataAsString(String path)  throws IOException;
}
