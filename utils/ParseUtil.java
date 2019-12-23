package capstone_project.av_service.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ParseUtil {
    public static String toPrettyFormat(String jsonString) 
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }
}
