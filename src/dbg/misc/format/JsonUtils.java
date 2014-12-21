package dbg.misc.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

public class JsonUtils<T> {

    public String toJsonArrayText(List<T> objects) {
        JsonArray array = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (T entry : objects) {
            array.add(gson.toJsonTree(entry));
        }

        return gson.toJson(array);
    }




}
