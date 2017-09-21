package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-21.
 */
public class CommandException extends RuntimeException {
    private final BaseCommand command;
    private final CommandSenderWrapper sender;

    public CommandException(BaseCommand command, CommandSenderWrapper sender) {
        this.command = command;
        this.sender = sender;
    }

    public BaseCommand getCommand() {
        return command;
    }

    public CommandSenderWrapper getSender() {
        return sender;
    }
}
