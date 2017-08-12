package kr.rvs.mclibrary.struct.command;

import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandProcessor extends Command implements PluginIdentifiableCommand {
    private final Plugin owner;
    private final MCCommand command;
    private final SubCommand subCommand;
    private final List<CommandStorage> storages = new ArrayList<>();

    public CommandProcessor(String name, String description, String usageMessage, List<String> aliases, Plugin owner, MCCommand command) throws NoSuchMethodException {
        super(name, description, usageMessage, aliases);

        this.owner = owner;
        this.command = command;
        this.subCommand = new SubCommand();

        init();
    }

    private void init() {
        for (Method method : command.getClass().getMethods()) {
            CommandArgs annot = method.getAnnotation(CommandArgs.class);
            if (annot == null)
                continue;

            String[] args = annot.args().split(" ");
            register(args, 0, new SubCommandStorage(annot), subCommand, method);
        }
    }

    private void register(String[] args, int index, SubCommandStorage annot, SubCommand subCommand, Method method) {
        SubCommand newSubCommand = new SubCommand();
        subCommand.put(args[index], newSubCommand);

        if (args.length - 1 > index) {
            register(args, index + 1, annot, newSubCommand, method);
        } else {
            CommandStorage storage = new CommandStorage(command, annot, method);
            storages.add(storage);
            newSubCommand.setStorage(storage);
        }
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        switch (subCommand.execute(sender, args, 0)) {
            case PERMISSION_DENIED:
            case HELP:
                sendHelpMessage(sender, new VolatileArrayList(Arrays.asList(args)));
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender, VolatileArrayList args) {
        StringBuilder builder = new StringBuilder(15);
        command.layout().writeHelpMessage(builder, command, storages, args);

        sender.sendMessage(MCUtils.colorize(builder.toString()));
    }

    @Override
    public Plugin getPlugin() {
        return owner;
    }

    enum CommandResult {
        DONE,
        PERMISSION_DENIED,
        HELP
    }

    class SubCommand {
        private final Map<String, SubCommand> subCommandMap = new HashMap<>();
        private CommandStorage storage;

        SubCommand() {
        }

        SubCommand(CommandStorage storage) {
            this.storage = storage;
        }

        CommandResult execute(CommandSender sender, String[] args, int index) {
            String arg = "";

            if (args.length > index) {
                arg = args[index];
            }

            SubCommand command = subCommandMap.get(arg);
            if (command != null) {
                return command.execute(sender, args, index + 1);
            } else {
                int remainSize = args.length - index;
                if (storage == null || !storage.getType().isValid(sender)
                        || (storage.getMin() != -1 && storage.getMin() > remainSize)
                        || (storage.getMax() != -1 && storage.getMax() < remainSize))
                    return CommandResult.HELP;

                String[] newArgs;

                if (args.length > 0) {
                    newArgs = new String[remainSize];
                    System.arraycopy(args, index, newArgs, 0, newArgs.length);
                } else {
                    newArgs = new String[0];
                }

                try {
                    storage.getMethod().invoke(storage.getCommand(), sender, new VolatileArrayList(Arrays.asList(newArgs)));
                    return CommandResult.DONE;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    Static.log(e);
                }
            }

            return CommandResult.HELP;
        }

        public SubCommand put(String cmd, SubCommand command) {
            return this.subCommandMap.put(cmd, command);
        }

        public SubCommand get(String cmd) {
            return this.subCommandMap.get(cmd);
        }

        public SubCommand remove(String cmd) {
            return this.subCommandMap.remove(cmd);
        }

        public CommandStorage getStorage() {
            return storage;
        }

        public void setStorage(CommandStorage storage) {
            this.storage = storage;
        }
    }
}
