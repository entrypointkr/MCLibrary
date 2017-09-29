package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.Executable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class CommandException extends RuntimeException {
    private final CommandSenderWrapper wrapper;
    private final CommandArguments arguments;
    private final Executable source;

    public CommandException(CommandSenderWrapper wrapper, CommandArguments arguments, Executable source) {
        this.wrapper = wrapper;
        this.arguments = arguments;
        this.source = source;
    }

    public CommandSenderWrapper getWrapper() {
        return wrapper;
    }

    public CommandArguments getArguments() {
        return arguments;
    }

    public Executable getSource() {
        return source;
    }
}
