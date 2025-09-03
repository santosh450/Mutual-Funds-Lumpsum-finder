package org.myprojects.utils;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.myprojects.reports.Log4jLogger;
import java.util.LinkedHashMap;

public final class JsonUtils {
    private JsonUtils(){}

    static String getMFName(Response response){
        JSONObject bodyJson = new JSONObject(response.getBody().asString());
        String mfName = bodyJson.getJSONObject("meta").getString("scheme_name");
        Log4jLogger.info("MF Name: "+mfName);
        return mfName;
    }

    static LinkedHashMap<String, Float> getNavData(Response response){
        LinkedHashMap<String, Float> navData = new LinkedHashMap<>();
        JSONObject bodyJson = new JSONObject(response.getBody().asString());
        JSONArray dataArray = bodyJson.getJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject item = dataArray.getJSONObject(i);
            navData.put(item.getString("date"), item.getFloat("nav"));
        }
        return navData;
    }

}
