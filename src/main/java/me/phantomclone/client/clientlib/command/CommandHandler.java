package me.phantomclone.client.clientlib.command;

import me.phantomclone.client.clientlib.plugin.Plugin;
import me.phantomclone.client.clientlib.utils.IChatUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler {

    private final IChatUtils chatUtils;

    private final LinkedList<Command> commands;

    public CommandHandler(IChatUtils chatUtils) {
        this.chatUtils = chatUtils;
        this.commands = new LinkedList<>();
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public void removeCommand(Command command) {
        this.commands.remove(command);
    }

    public void removeCommands(Plugin plugin) {
        ArrayList<Command> list = new ArrayList<>();
        getCommandList().stream().filter(command -> command.getPlugin().equals(plugin)).forEach(command -> list.add(command));
        list.forEach(this::removeCommand);
    }

    public boolean callCommand(String line) {
        String raw = line.substring(1);
        String[] split = raw.split(" ");

        if (split.length == 0) return false;

        String cmdName = split[0];

        Command command = getCommandList().stream().filter(cmd -> cmd.match(cmdName)).findFirst().orElse(null);

        try {
            if (command == null) {
                chatUtils.sendWithPrefix("§c'" + cmdName + "' doesn't exist");
                return false;
            } else {
                String[] args = new String[split.length - 1];

                System.arraycopy(split, 1, args, 0, split.length - 1);

                command.run(split[0], args);
                return true;
            }
        } catch (CommandException e) {
            chatUtils.displayMessage("§c" + e.getMessage());
        }
        return true;
    }

    public Collection<String> autoComplete(String currCmd) {
        String raw = currCmd.substring(1);
        String[] split = raw.split(" ");

        List<String> ret = new ArrayList<>();


        Command currentCommand = split.length >= 1 ? getCommandList().stream().filter(cmd -> cmd.match(split[0])).findFirst().orElse(null) : null;

        if (split.length >= 2 || currentCommand != null && currCmd.endsWith(" ")) {

            if (currentCommand == null) return ret;

            String[] args = new String[split.length - 1];

            System.arraycopy(split, 1, args, 0, split.length - 1);

            List<String> autocomplete = currentCommand.autocomplete(args.length + (currCmd.endsWith(" ") ? 1 : 0), args);

            return autocomplete == null ? new ArrayList<>() : autocomplete;
        } else if (split.length == 1) {
            for (Command command : getCommandList()) {
                ret.addAll(command.getNameAndAliases());
            }

            return ret.stream().map(str -> "." + str).filter(str -> str.toLowerCase().startsWith(currCmd.toLowerCase())).collect(Collectors.toList());
        }

        return ret;
    }

    public LinkedList<Command> getCommandList() {
        return commands;
    }
}