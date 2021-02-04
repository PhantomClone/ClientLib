package me.phantomclone.client.clientlib.module;

import me.phantomclone.client.clientlib.ClientLib;
import me.phantomclone.client.clientlib.plugin.Plugin;

import java.util.*;

public class ModuleHandler {

    private final LinkedHashMap<Plugin, LinkedList<Module>> modules;

    private final ClientLib client;

    public ModuleHandler(ClientLib client) {
        this.modules = new LinkedHashMap<>();
        this.client = client;
    }

    public void registerModule(Plugin plugin, Module module) {
        if (!this.modules.containsKey(plugin)) {
            this.modules.put(plugin, new LinkedList<>());
        }
        this.modules.get(plugin).add(module);
        this.client.getValueHandler().registerObject(module.getModuleName(), module);
    }

    public void unregisterModules() {
        this.modules.clear();
    }

    public void unregisterModule(Module module) {
        for (Map.Entry<Plugin, LinkedList<Module>> set : modules.entrySet()) {
            if (set.getValue().contains(module)) {
                set.getValue().remove(module);
                this.client.getValueHandler().saveValues(module);
            }
        }
    }

    public void unregisterModule(Plugin plugin) {
        ArrayList<Module> toUnRegister = new ArrayList<>();
        if (this.modules.get(plugin) != null && !this.modules.get(plugin).isEmpty()) {
            this.modules.get(plugin).stream().forEach(toUnRegister::add);
        }
        toUnRegister.forEach(this::unregisterModule);
        this.modules.remove(plugin);
    }

    public ArrayList<Module> getLoadedModules() {
        ArrayList<Module> modules = new ArrayList<>();
        this.modules.values().forEach(modules::addAll);
        return modules;
    }

    public Module getModule(Class<? extends Module> targetModule) {
        return modules.values().stream().flatMap(Collection::stream).filter(currentModule -> currentModule.getClass() == targetModule).findFirst().orElse(null);
    }

    public Module getModule(String moduleName) {
        return modules.values().stream().flatMap(Collection::stream).filter(currentModule -> currentModule.getModuleName().equalsIgnoreCase(moduleName)).findFirst().orElse(null);
    }
}
