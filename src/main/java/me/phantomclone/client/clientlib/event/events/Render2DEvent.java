package me.phantomclone.client.clientlib.event.events;

import me.phantomclone.client.clientlib.event.Event;
import me.phantomclone.client.clientlib.event.State;

public class Render2DEvent extends Event {

    private final State state;

    public Render2DEvent(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

}
