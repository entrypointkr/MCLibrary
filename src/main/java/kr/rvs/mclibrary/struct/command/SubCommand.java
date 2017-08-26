package kr.rvs.mclibrary.struct.command;

import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.bukkit.command.CommandSenderWrapper;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Junhyeong Lim on 2017-08-26.
 */
public class SubCommand {
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
                return new CommandResult(CommandResult.State.HELP, storage);

            if (storage.hasPermission()
                    && !sender.hasPermission(storage.getPermission()))
                return new CommandResult(CommandResult.State.PERMISSION_DENIED, storage);

            String[] newArgs;

            if (args.length > 0) {
                newArgs = new String[remainSize];
                System.arraycopy(args, index, newArgs, 0, newArgs.length);
            } else {
                newArgs = new String[0];
            }

            try {
                storage.getMethod().invoke(storage.getCommand(), new CommandSenderWrapper(sender), new VolatileArrayList(Arrays.asList(newArgs)));
                return new CommandResult(CommandResult.State.DONE, storage);
            } catch (IllegalAccessException | InvocationTargetException e) {
                Static.log(e);
            }
        }

        return new CommandResult(CommandResult.State.HELP, storage);
    }

    public SubCommand put(String cmd, SubCommand command) {
        return this.subCommandMap.put(cmd, command);
    }

    public SubCommand get(String cmd) {
        return this.subCommandMap.get(cmd);
    }

    public boolean contains(String key) {
        return this.subCommandMap.containsKey(key);
    }

    public List<String> getStartsWithKeys(String cmd) {
        List<String> ret = new ArrayList<>();
        subCommandMap.keySet().forEach(key -> {
            if (key.startsWith(cmd))
                ret.add(key);
        });
        return ret;
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