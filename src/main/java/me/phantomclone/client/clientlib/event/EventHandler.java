package me.phantomclone.client.clientlib.event;

import me.phantomclone.client.clientlib.plugin.Plugin;

import java.util.ArrayList;

public class EventHandler {

    private final ArrayList<ListenerExecutor> executors;

    public EventHandler() {
        this.executors = new ArrayList<>();
    }

    public void registerListener(Plugin plugin, Class<? extends Event> clazz, EventListener listener) {
        ListenerExecutor executor = new ListenerExecutor(plugin, clazz, listener);

        getExecutors().add(executor);
    }

    public void unregisterListener(EventListener listener) {
        ArrayList<ListenerExecutor> toRemove = new ArrayList<>();
        getExecutors().stream().filter(e -> e.getListener().equals(listener)).forEach(toRemove::add);
        toRemove.forEach(e -> getExecutors().remove(e));
    }

    public void unregisterClass(Class<? extends Event> clazz) {
        this.executors.stream().filter(listenerExecutor -> listenerExecutor.getClazz().equals(clazz)).forEach(executors -> unregisterListener(executors.getListener()));
    }

    public void unregisterListeners() {
        this.executors.clear();
    }

    public void unregisterListener(Plugin plugin) {
        ArrayList<ListenerExecutor> toRemove = new ArrayList<>();
        getExecutors().stream().filter(e -> e.getPlugin().equals(plugin)).forEach(toRemove::add);
        toRemove.forEach(e -> getExecutors().remove(e));
        //getExecutors().stream().filter(ex -> ex.getPlugin().equals(plugin)).forEach(ex -> unregisterListener(ex.getListener()));
    }

    public void callEvent(Event event) {
        ArrayList<ListenerExecutor> copyList = new ArrayList<>(getExecutors());
        copyList.forEach(e -> e.execute(event));
    }

    public ArrayList<ListenerExecutor> getExecutors() {
        return executors;
    }
}
