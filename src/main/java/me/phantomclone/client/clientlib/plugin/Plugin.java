package me.phantomclone.client.clientlib.plugin;

import me.phantomclone.client.clientlib.ClientLib;

public abstract class Plugin {

    private ClientLib client;

    private boolean enabled;

    public void load() {}
    public void unload() {}
    public void enable() {}
    public void disable() {}

    public void setClient(ClientLib client) {
        this.client = client;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ClientLib getClient() {
        return client;
    }
}
