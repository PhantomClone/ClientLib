package me.phantomclone.client.clientlib.event.events;

import me.phantomclone.client.clientlib.event.CancellableEvent;

public class PacketEvent extends CancellableEvent {

    private final Object packet;

    public PacketEvent(Object packet) {
        this.packet = packet;
    }

    public Object getPacket() {
        return packet;
    }
}