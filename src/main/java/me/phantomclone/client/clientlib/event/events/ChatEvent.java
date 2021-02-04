package me.phantomclone.client.clientlib.event.events;

import me.phantomclone.client.clientlib.event.CancellableEvent;

public class ChatEvent extends CancellableEvent {

    private final ChatState chatState;
    private String message;

    public ChatEvent(ChatState chatState, String message) {
        this.chatState = chatState;
        this.message = message;
    }

    public ChatState getChatState() {
        return chatState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum ChatState {
        SERVERTOCLIENT, CLIENTTOSERVER
    }

}
