package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import kr.rvs.mclibrary.bukkit.command.SubCommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-21.
 */
public class InvalidUsageException extends CommandException {
    private final SubCommand subCommand;

    public InvalidUsageException(BaseCommand subCommand, CommandSenderWrapper sender, SubCommand command1) {
        super(subCommand, sender);
        this.subCommand = command1;
    }

    public SubCommand getSubCommand() {
        return subCommand;
    }
}
