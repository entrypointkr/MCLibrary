package kr.rvs.mclibrary.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public enum CommandType {
    DEFAULT, PLAYER_ONLY, CONSOLE_ONLY;

    public boolean isValid(CommandSender sender) {
        switch (this) {
            case DEFAULT:
                return true;
            case PLAYER_ONLY:
                return sender instanceof Player;
            case CONSOLE_ONLY:
                return sender instanceof ConsoleCommandSender;
            default:
                return false;
        }
    }
}
