package me.phantomclone.client.clientlib.event.events;

import me.phantomclone.client.clientlib.event.CancellableEvent;

public class CustomEvent extends CancellableEvent {

    private int customId;
    private Object object;

    public CustomEvent(int customId, Object object) {
        this.customId = customId;
        this.object = object;
    }

    public int getCustomId() {
        return customId;
    }

    public Object getObject() {
        return object;
    }
}
