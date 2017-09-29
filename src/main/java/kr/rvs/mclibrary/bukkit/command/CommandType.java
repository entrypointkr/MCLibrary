package kr.rvs.mclibrary.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.function.Function;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public enum CommandType {
    DEFAULT(sender -> true),
    PLAYER(sender -> sender instanceof Player),
    CONSOLE(sender -> sender instanceof ConsoleCommandSender)
    ;

    private final Function<CommandSender, Boolean> function;

    CommandType(Function<CommandSender, Boolean> function) {
        this.function = function;
    }

    public boolean isValid(CommandSender sender) {
        return function.apply(sender);
    }
}
