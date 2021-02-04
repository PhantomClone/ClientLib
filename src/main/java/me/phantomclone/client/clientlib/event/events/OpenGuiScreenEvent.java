package me.phantomclone.client.clientlib.event.events;

import me.phantomclone.client.clientlib.event.CancellableEvent;

public class OpenGuiScreenEvent extends CancellableEvent {

    private Object oldGuiScreen;
    private Object newGuiScreen;

    public OpenGuiScreenEvent(Object oldGuiScreen, Object newGuiScreen) {
        this.oldGuiScreen = oldGuiScreen;
        this.newGuiScreen = newGuiScreen;
    }

    public void setNewGuiScreen(Object newGuiScreen) {
        this.newGuiScreen = newGuiScreen;
    }

    public Object getNewGuiScreen() {
        return newGuiScreen;
    }

    public void setOldGuiScreen(Object oldGuiScreen) {
        this.oldGuiScreen = oldGuiScreen;
    }

    public Object getOldGuiScreen() {
        return oldGuiScreen;
    }
}
