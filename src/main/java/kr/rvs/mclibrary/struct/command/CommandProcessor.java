package kr.rvs.mclibrary.struct.command;

import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
public class CommandProcessor extends Command {
    private static CommandLayout commandLayout = new CommandLayout() {
    };

    private final MCCommand command;
    private final SubCommand subCommand;
    private final List<CommandStorage> storages = new ArrayList<>();

    public CommandProcessor(String name, String description, String usageMessage, List<String> aliases, MCCommand command) {
        super(name, description, usageMessage, aliases);

        this.command = command;
        this.subCommand = new SubCommand();
        init();
    }

    public static CommandLayout getCommandLayout() {
        return commandLayout;
    }

    public static void setCommandLayout(CommandLayout commandLayout) {
        CommandProcessor.commandLayout = commandLayout;
    }

    private void init() {
        for (Method method : command.getClass().getMethods()) {
            CommandArgs annot = method.getAnnotation(CommandArgs.class);
            if (annot == null)
                continue;

            String[] args = annot.args().split(" ");
            register(args, 0, annot, subCommand, method);
        }
    }

    private void register(String[] args, int index, CommandArgs annot, SubCommand subCommand, Method method) {
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
        if (!subCommand.execute(sender, args, 0)) {
            sendHelpMessage(sender);
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        StringBuilder builder = new StringBuilder();
        String prefix = commandLayout.prefix();
        if (prefix != null && !prefix.isEmpty())
            builder.append(prefix);

        for (CommandStorage storage : storages) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            String layout = commandLayout.content();
            if (layout != null && !layout.isEmpty()) {
                builder.append(
                        layout.replace(CommandLayout.LABEL_KEY, getLabel())
                                .replace(CommandLayout.ARGS_KEY, storage.getArgs())
                                .replace(CommandLayout.USAGE_KEY, storage.getUsage())
                                .replace(CommandLayout.DESCRIPTION_KEY, storage.getDescription())
                );
            }
        }

        String suffix = commandLayout.suffix();
        if (suffix != null && !suffix.isEmpty())
            builder.append("\n").append(suffix);
        sender.sendMessage(MCUtils.colorize(builder.toString()));
    }

    class SubCommand {
        private final Map<String, SubCommand> subCommandMap = new HashMap<>();
        private CommandStorage storage;

        SubCommand() {
        }

        SubCommand(CommandStorage storage) {
            this.storage = storage;
        }

        boolean execute(CommandSender sender, String[] args, int index) {
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
                    return false;

                String[] newArgs;

                if (args.length > 0) {
                    newArgs = new String[remainSize];
                    System.arraycopy(args, index, newArgs, 0, newArgs.length);
                } else {
                    newArgs = new String[0];
                }

                try {
                    storage.getMethod().invoke(storage.getCommand(), sender, new VolatileArrayList(Arrays.asList(newArgs)));
                    return true;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    Static.log(e);
                }
            }

            return false;
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
