package me.phantomclone.client.clientlib;

import me.phantomclone.client.clientlib.command.Command;
import me.phantomclone.client.clientlib.command.CommandHandler;
import me.phantomclone.client.clientlib.event.EventHandler;
import me.phantomclone.client.clientlib.module.ModuleHandler;
import me.phantomclone.client.clientlib.plugin.Plugin;
import me.phantomclone.client.clientlib.plugin.PluginHandler;
import me.phantomclone.client.clientlib.utils.ClientInfo;
import me.phantomclone.client.clientlib.utils.FileHandler;
import me.phantomclone.client.clientlib.utils.IChatUtils;
import me.phantomclone.client.clientlib.utils.value.ValueHandler;

import java.util.ArrayList;
import java.util.List;

public class ClientLib {

    private final ClientInfo clientInfo;

    private final FileHandler fileHandler;
    private final ModuleHandler moduleHandler;
    private final EventHandler eventHandler;
    private final ValueHandler valueHandler;
    private final PluginHandler pluginHandler;
    private final CommandHandler commandHandler;

    private final IChatUtils chatUtils;

    public ClientLib(ClientInfo clientInfo, IChatUtils chatUtils) {
        this.clientInfo = clientInfo;
        this.chatUtils = chatUtils;

        this.fileHandler = new FileHandler("./" + clientInfo.getClientName() + "/" + clientInfo.getMinecraftVersion() + "/" + clientInfo.getClientVersion() + "/files/");
        this.moduleHandler = new ModuleHandler(this);
        this.eventHandler = new EventHandler();
        this.valueHandler = new ValueHandler(this.fileHandler);
        this.pluginHandler = new PluginHandler(this, "./" + clientInfo.getClientName() + "/" + clientInfo.getMinecraftVersion() + "/" + clientInfo.getClientVersion() + "/plugins/");
        this.commandHandler = new CommandHandler(this.chatUtils);
    }

    public void registerPluginCommand(Plugin clientPlugin) {
        this.commandHandler.addCommand(new Command(clientPlugin, "plugin", "p") {
            @Override
            public void run(String alias, String[] args) {
                switch (args.length) {
                    case 1:
                        switch (args[0].toLowerCase()) {
                            case "reload":
                            case "rl":
                                getPluginHandler().disablePlugins();
                                getPluginHandler().disablePlugins();
                                getPluginHandler().unloadPlugins();
                                getPluginHandler().loadPlugins();
                                getPluginHandler().enablePlugins();
                                getChatUtils().sendWithPrefix("Alle Plugins wurden neu geladen!");
                                return;
                        }
                        break;
                    case 2:
                        switch (args[0].toLowerCase()) {
                            case "load":
                                if (!getPluginHandler().loadPlugin(args[1])) {
                                    getChatUtils().sendWithPrefix("§7Plugin §c" + args[1] + " §7konnte nicht geladen werden!");
                                } else {
                                    getChatUtils().sendWithPrefix("§7Plugin §a" + args[1] + " §7wurde geladen!");
                                }
                                return;
                            case "unload":
                                if (!getPluginHandler().unloadPlugin(args[1])) {
                                    getChatUtils().sendWithPrefix("§7Plugin §c" + args[1] + " §7konnte nicht entladen werden!");
                                } else {
                                    getChatUtils().sendWithPrefix("§7Plugin §a" + args[1] + " §7wurde entladen!");
                                }
                                return;
                            case "enable":
                                if (!getPluginHandler().enablePlugin(args[1])) {
                                    getChatUtils().sendWithPrefix("§7Plugin §c" + args[1] + " §7konnte nicht enabled werden!");
                                } else {
                                    getChatUtils().sendWithPrefix("§7Plugin §a" + args[1] + " §7wurde enabled!");
                                }
                                return;
                            case "disable":
                                if (!getPluginHandler().disablePlugin(args[1])) {
                                    getChatUtils().sendWithPrefix("§7Plugin §c" + args[1] + " §7konnte nicht disabled werden!");
                                } else {
                                    getChatUtils().sendWithPrefix("§7Plugin §a" + args[1] + " §7wurde disabled!");
                                }
                                return;
                            case "reload":
                                if (!getPluginHandler().disablePlugin(args[1])) {
                                    getChatUtils().sendWithPrefix("Use: ." + alias + " <load/unload/enable/diable> <PluginName>");
                                    return;
                                }
                                if (!getPluginHandler().unloadPlugin(args[1])) {
                                    getChatUtils().sendWithPrefix("§7Plugin §c" + args[1] + " §7konnte nicht entladen werden!");
                                    return;
                                }
                                if (!getPluginHandler().loadPlugin(args[1])) {
                                    getChatUtils().sendWithPrefix("§7Plugin §c" + args[1] + " §7konnte nicht geladen werden!");
                                    return;
                                }
                                if (!getPluginHandler().enablePlugin(args[1])) {
                                    getChatUtils().sendWithPrefix("§7Plugin §c" + args[1] + " §7konnte nicht enabled!");
                                    return;
                                }
                                getChatUtils().sendWithPrefix("§7Plugin §a" + args[1] + " wurde neu geloaded!");
                                return;
                        }
                        break;
                }
                getChatUtils().sendWithPrefix("Use: ." + alias + " <load/unload/enable/disable/reload> <PluginName>");
            }

            @Override
            public List<String> autocomplete(int arg, String[] args) {
                return new ArrayList<>();
            }
        });
    }

    public void initPlugins() {
        this.pluginHandler.loadPlugins();
        this.pluginHandler.enablePlugins();
    }

    public void shutDown() {
        this.moduleHandler.getLoadedModules().forEach(this.valueHandler::saveValues);
        this.pluginHandler.disablePlugins();
        this.pluginHandler.unloadPlugins();
        this.eventHandler.unregisterListeners();
        this.moduleHandler.unregisterModules();
    }

    public ModuleHandler getModuleHandler() {
        return this.moduleHandler;
    }

    public EventHandler getEventHandler() {return this.eventHandler;}

    public ValueHandler getValueHandler() {
        return this.valueHandler;
    }

    public FileHandler getFileHandler() {
        return this.fileHandler;
    }

    public PluginHandler getPluginHandler() {
        return pluginHandler;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public IChatUtils getChatUtils() {
        return chatUtils;
    }
}
