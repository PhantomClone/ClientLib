package me.phantomclone.client.clientlib.event;

import me.phantomclone.client.clientlib.plugin.Plugin;

public class ListenerExecutor {

    private Plugin plugin;
    private final Class<? extends Event> clazz;
    private final EventListener listener;


    public ListenerExecutor(Plugin plugin, Class<? extends Event> clazz, EventListener listener) {
        this.plugin = plugin;
        this.clazz = clazz;
        this.listener = listener;
    }

    public void execute(Event event) {
        if (this.clazz.equals(event.getClass()))
            this.listener.on(event);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Class<? extends Event> getClazz() {
        return clazz;
    }

    public EventListener getListener() {
        return listener;
    }
}
