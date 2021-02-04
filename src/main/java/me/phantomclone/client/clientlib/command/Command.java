package me.phantomclone.client.clientlib.command;

import me.phantomclone.client.clientlib.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command {

    private Plugin plugin;
    private String name;
    private String[] aliases;

    protected Command(Plugin plugin, String name, String... aliases) {
        this.plugin = plugin;
        this.name = name;
        this.aliases = aliases;
    }

    public abstract void run(String alias, String[] args);

    public abstract List<String> autocomplete(int arg, String[] args);

    public boolean match(String name) {
        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(name)) return true;
        }
        return this.name.equalsIgnoreCase(name);
    }

    public List<String> getNameAndAliases() {
        List<String> l = new ArrayList<>();
        l.add(name);
        l.addAll(Arrays.asList(aliases));
        return l;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}