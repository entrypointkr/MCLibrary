package kr.rvs.mclibrary.struct.command;

import kr.rvs.mclibrary.util.collection.VolatileArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandProcessor extends Command {
    private final MCCommand command;
    private final SubCommand subCommand;

    public CommandProcessor(String name, String description, String usageMessage, List<String> aliases, MCCommand command) {
        super(name, description, usageMessage, aliases);

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
            register(args, 0, annot, subCommand, method);
        }
    }

    private void register(String[] args, int index, CommandArgs annot, SubCommand subCommand, Method method) {
        if (args.length <= index)
            return;
        CommandStorage storage = new CommandStorage(command, annot, method);
        SubCommand newSubCommand = subCommand.put(args[index], new SubCommand(storage));

        if (args.length > index) {
            register(args, index + 1, annot, newSubCommand, method);
        }
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        subCommand.execute(sender, args, 0);
        return true;
    }

    class SubCommand {
        private final Map<String, SubCommand> subCommandMap = new HashMap<>();
        private CommandStorage storage;

        SubCommand() {
        }

        SubCommand(CommandStorage storage) {
            this.storage = storage;
        }

        void execute(CommandSender sender, String[] args, int index) {
            if (args.length <= index)
                return;

            String arg = args[index];
            SubCommand command = subCommandMap.get(arg);
            if (command != null) {
                command.execute(sender, args, index + 1);
            } else {
                int remainSize = args.length - index;
                if (!storage.getType().isValid(sender)
                        || (storage.getMax() != -1 && storage.getMin() > remainSize)
                        || (storage.getMax() != -1 && storage.getMax() < remainSize))
                    return;
                try {
                    String[] newArgs = new String[remainSize];
                    System.arraycopy(args, index, newArgs, 0, newArgs.length);
                    storage.getMethod().invoke(storage.getCommand(), sender, new VolatileArrayList(Arrays.asList(newArgs)));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        public SubCommand put(String cmd, SubCommand command) {
            this.subCommandMap.put(cmd, command);
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
