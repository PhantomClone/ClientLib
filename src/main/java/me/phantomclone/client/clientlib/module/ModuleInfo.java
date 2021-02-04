package me.phantomclone.client.clientlib.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    String moduleName();
    String moduleDescription();
    ModuleCategory moduleCategory();
    int defaultKey() default 0;
    boolean canEnable() default true;

}
