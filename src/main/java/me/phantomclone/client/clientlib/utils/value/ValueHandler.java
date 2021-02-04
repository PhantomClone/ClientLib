package me.phantomclone.client.clientlib.utils.value;

import me.phantomclone.client.clientlib.module.Module;
import me.phantomclone.client.clientlib.utils.FileHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueHandler {

    private final FileHandler filehandler;

    private HashMap<String, List<Value>> valueMap = new HashMap<>();

    public ValueHandler(FileHandler filehandler) {
        this.filehandler = filehandler;
    }

    public void registerObject(String name, Object object) {
        List<Value> values = new ArrayList<>();
        for (final Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                final Object obj = field.get(object);

                if (obj instanceof Value) {
                    values.add((Value) obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        valueMap.put(name, values);
        if (object instanceof Module) {
            loadValues((Module) object, values);
        }
    }

    public void loadValues(Module module, List<Value> values) {
        this.filehandler.loadModule(module, values);
    }

    public void saveValues(Module module) {
        this.filehandler.saveModule(module, valueMap.get(module.getModuleName()));
    }

    public List<Value> getAllValuesFrom(String name) {
        for (Map.Entry<String, List<Value>> stringListEntry : valueMap.entrySet()) {
            if (stringListEntry.getKey().equalsIgnoreCase(name)) return stringListEntry.getValue();
        }
        return null;
    }

    public HashMap<String, List<Value>> getAllValues() {
        return valueMap;
    }

    public Value get(String owner, String name) {
        List<Value> found = getAllValuesFrom(owner);

        if (found == null) return null;

        return found.stream().filter(val -> name.equalsIgnoreCase(val.getName())).findFirst().orElse(null);
    }

}
