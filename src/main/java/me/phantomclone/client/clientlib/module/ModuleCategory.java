package me.phantomclone.client.clientlib.module;

public enum ModuleCategory {

    NONE("None"), PLAYER("Player"), COMBAT("Combat"), MOVEMENT("Movement"), WORLD("World"), EXPLOIT("Exploit"), RENDER("Render");

    private String name;

    ModuleCategory(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}