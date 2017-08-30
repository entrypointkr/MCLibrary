package kr.rvs.mclibrary.struct.command;

import kr.rvs.mclibrary.struct.command.layout.CommandLayout;
import kr.rvs.mclibrary.struct.command.layout.CommandLayoutStorage;
import kr.rvs.mclibrary.util.bukkit.command.CommandSenderWrapper;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandProcessor extends Command implements PluginIdentifiableCommand {
    private static final Map<String, CommandLayout> layoutMap = new HashMap<>();

    private final Plugin owner;
    private final MCCommand command;
    private final SubCommand subCommand;
    private final List<CommandStorage> storages = new ArrayList<>();

    public static Map<String, CommandLayout> getLayoutMap() {
        return layoutMap;
    }

    public CommandProcessor(String name, String description, String usageMessage, List<String> aliases, Plugin owner, MCCommand command) {
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

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        CommandSenderWrapper wrapped = new CommandSenderWrapper(sender);
        CommandResult result = subCommand.execute(sender, args, 0);
        switch (result.getState()) {
            case PERMISSION_DENIED:
                sendMessageWithPrefix(wrapped, getLayout().permissionDeniedMessage()
                        .replace(CommandLayout.PERMISSION_NODE, result.getStorage().getPermission()));
                break;
            case HELP:
                StringBuilder builder = new StringBuilder(15);
                getLayout().writeHelpMessage(new CommandLayoutStorage(builder, sender, command, storages, new VolatileArrayList(Arrays.asList(args))));

                wrapped.sendMessage(builder.toString().split("\n"));
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> ret = subCommand.tabComplete(0, args);
        return ret.isEmpty() ? super.tabComplete(sender, alias, args) : ret;
    }

    private void register(String[] args, int index, SubCommandStorage annot, SubCommand subCommand, Method method) {
        String arg = args[index];
        SubCommand newSubCommand;
        if (subCommand.contains(arg)) {
            newSubCommand = subCommand.get(arg);
        } else {
            newSubCommand = new SubCommand();
            subCommand.put(arg, newSubCommand);
        }

        if (args.length - 1 > index) {
            register(args, index + 1, annot, newSubCommand, method);
        } else {
            CommandStorage storage = new CommandStorage(command, annot, method);
            storages.add(storage);
            newSubCommand.setStorage(storage);
        }
    }

    private CommandLayout getLayout() {
        return layoutMap.computeIfAbsent(getLabel(), k -> command.layout());
    }

    private void sendMessageWithPrefix(CommandSender sender, String msg) {
        CommandLayout layout = layoutMap.computeIfAbsent(getLabel(), k -> command.layout());
        sender.sendMessage(layout.prefix().replace(CommandLayout.PLUGIN_NAME, owner.getName()) + msg);
    }

    @Override
    public Plugin getPlugin() {
        return owner;
    }
}
