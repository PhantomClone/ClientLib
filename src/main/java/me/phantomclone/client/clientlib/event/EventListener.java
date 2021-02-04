package me.phantomclone.client.clientlib.event;

public interface EventListener<T extends Event>  {

    void on(T event);

}