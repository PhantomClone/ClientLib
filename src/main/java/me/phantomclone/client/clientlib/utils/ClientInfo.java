package me.phantomclone.client.clientlib.utils;

public class ClientInfo {

    private final String clientName;
    private final String clientVersion;
    private final String minecraftVersion;

    public ClientInfo(String clientName, String clientVersion, String minecraftVersion) {
        this.clientName = clientName;
        this.clientVersion = clientVersion;
        this.minecraftVersion = minecraftVersion;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getMinecraftVersion() {
        return minecraftVersion;
    }
}
