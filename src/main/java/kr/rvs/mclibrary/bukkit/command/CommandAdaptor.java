package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class CommandAdaptor extends Command implements PluginIdentifiableCommand {
    private final ICommand command;
    private final Plugin plugin;
    private CommandExceptionHandler exceptionHandler;

    public CommandAdaptor(String name, String description, String usageMessage, List<String> aliases, ICommand command, Plugin plugin, CommandExceptionHandler exceptionHandler) {
        super(name, description, usageMessage, aliases);
        this.command = command;
        this.plugin = plugin;

        setExceptionHandler(exceptionHandler);
    }

    public CommandAdaptor(String name, String description, String usageMessage, List<String> aliases, ICommand command, Plugin plugin) {
        this(name, description, usageMessage, aliases, command, plugin, new DefaultExceptionHandler());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        CommandSenderWrapper wrapper = new CommandSenderWrapper(sender);
        CommandArguments arguments = new CommandArguments(Arrays.asList(args));
        try {
            command.execute(wrapper, arguments);
        } catch (CommandNotFoundException ex) {
            exceptionHandler.handle(ex);
        } catch (InvalidUsageException ex) {
            exceptionHandler.handle(ex);
        } catch (PermissionDeniedException ex) {
            exceptionHandler.handle(ex);
        } catch (Exception ex) {
            exceptionHandler.handle(ex);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws
            IllegalArgumentException {
        CommandSenderWrapper wrapper = new CommandSenderWrapper(sender);
        CommandArguments arguments = new CommandArguments(Arrays.asList(args));
        List<String> complete = command.tabComplete(wrapper, arguments);
        return complete != null && !complete.isEmpty() ? complete : super.tabComplete(sender, alias, args);
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public ICommand getCommand() {
        return command;
    }

    public CommandExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(CommandExceptionHandler exceptionHandler) {
        this.exceptionHandler = Objects.requireNonNull(exceptionHandler);
        exceptionHandler.init(this);
    }
}
