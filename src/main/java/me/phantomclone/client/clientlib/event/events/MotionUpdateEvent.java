package me.phantomclone.client.clientlib.event.events;

import me.phantomclone.client.clientlib.event.CancellableEvent;
import me.phantomclone.client.clientlib.event.State;

public class MotionUpdateEvent extends CancellableEvent {

    private final State state;

    private float yaw;
    private float pitch;

    private boolean onGround;

    private boolean cach;

    public MotionUpdateEvent(float yaw, float pitch, State state, boolean onGround) {
        this.state = state;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.cach = true;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public State getState() {
        return state;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isCach() {
        return cach;
    }

    public void setCach(boolean cach) {
        this.cach = cach;
    }
}
