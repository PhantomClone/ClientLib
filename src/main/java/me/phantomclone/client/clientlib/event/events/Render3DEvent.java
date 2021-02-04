package me.phantomclone.client.clientlib.event.events;

import me.phantomclone.client.clientlib.event.Event;

public class Render3DEvent extends Event {

    public float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
