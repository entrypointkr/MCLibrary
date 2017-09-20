package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public enum CommandType {
    DEFAULT,
    PLAYER_ONLY,
    CONSOLE_ONLY
    ;

    public boolean isValid(CommandSender sender) {
        switch (this) {
            case DEFAULT:
                return true;
            case PLAYER_ONLY:
                return sender instanceof Player;
            case CONSOLE_ONLY:
                return sender instanceof ConsoleCommandSender;
        }
        return false;
    }

    public boolean isValid(CommandSenderWrapper wrapper) {
        return isValid(wrapper.getSender());
    }
}
