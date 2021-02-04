package me.phantomclone.client.clientlib.event.events;

import me.phantomclone.client.clientlib.event.Event;
import org.lwjgl.input.Keyboard;

public class KeyEvent extends Event {

    private final int key;
    private String stringKey;

    public KeyEvent(int key) {
        this.key = key;
        this.stringKey = Keyboard.getKeyName(key);
    }

    public int getKey() {
        return key;
    }

    public String getStringKey() {
        return stringKey;
    }

}