package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import kr.rvs.mclibrary.bukkit.command.internal.ICommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-21.
 */
public class CommandNotFoundException extends CommandException {
    public CommandNotFoundException(BaseCommand command, ICommand source, CommandSenderWrapper sender) {
        super(command, sender);
    }
}
