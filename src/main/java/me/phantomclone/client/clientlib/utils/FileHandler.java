package me.phantomclone.client.clientlib.utils;

import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import me.phantomclone.client.clientlib.module.Module;
import me.phantomclone.client.clientlib.utils.value.Value;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileHandler {

    private final String folder;

    public FileHandler(String folder) {
        this.folder = folder;
        File file = new File(folder);
        if (!file.exists())
            file.mkdirs();
    }

    public void loadModule(Module module, List<Value> list) {
        try {
            File file = new File(folder + module.getModuleName() + ".json");
            if (!file.exists())
                return;
            JsonObject json = (JsonObject) new JsonParser().parse(new InputStreamReader(new FileInputStream(file)));

            JsonElement state = json.get("state");
            if (state instanceof JsonPrimitive && ((JsonPrimitive) state).isBoolean()) {
                module.setState(state.getAsBoolean());
            }
            JsonElement keybind = json.get("keybind");
            if (keybind instanceof JsonPrimitive && ((JsonPrimitive) keybind).isNumber()) {
                module.setKeyBind(keybind.getAsInt());
            }
            list.forEach(value -> {
                JsonElement element = json.get(value.getName());
                if (element != null && element instanceof JsonObject) {
                    value.fromJsonObject((JsonObject) element);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveModule(Module module, List<Value> list) {
        JsonObject json = new JsonObject();
        json.addProperty("state", module.getState());
        json.addProperty("keybind", module.getKeyBind());

        list.forEach(value -> value.addToJsonObject(json));

        try {
            Files.write(json.toString().getBytes(StandardCharsets.UTF_8), new File(folder + module.getModuleName() + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
