package me.phantomclone.client.clientlib.module;

import me.phantomclone.client.clientlib.ClientLib;

public abstract class Module {

    private final String moduleName;
    private final String moduleDescription;
    private final ModuleCategory moduleCategory ;
    private final boolean canEnable;

    private final ClientLib client;

    private int keyBind = getClass().getAnnotation(ModuleInfo.class).defaultKey();

    private boolean state;

    public Module(ClientLib client) {
        this.client = client;
        this.moduleName = getClass().getAnnotation(ModuleInfo.class).moduleName();
        this.moduleDescription = getClass().getAnnotation(ModuleInfo.class).moduleDescription();
        this.moduleCategory = getClass().getAnnotation(ModuleInfo.class).moduleCategory();
        this.canEnable = getClass().getAnnotation(ModuleInfo.class).canEnable();
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void setState(boolean state) {
        if(state && !this.state && this.canEnable) {
            this.state = true;
            onEnable();
        }
        if (!state && this.state) {
            this.state = false;
            onDisable();
        }
    }

    public ClientLib getClient() {
        return client;
    }

    public boolean getState() {
        return state;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public ModuleCategory getModuleCategory() {
        return moduleCategory;
    }

    public boolean isCanEnable() {
        return canEnable;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }
}
