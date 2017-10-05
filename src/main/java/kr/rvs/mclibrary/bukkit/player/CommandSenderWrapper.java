package kr.rvs.mclibrary.bukkit.player;

import kr.rvs.mclibrary.bukkit.MCUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-09-17.
 */
public class CommandSenderWrapper {
    private final CommandSender sender;

    public CommandSenderWrapper(CommandSender sender) {
        this.sender = sender;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public boolean isConsole() {
        return sender instanceof ConsoleCommandSender;
    }

    public Player getPlayer() {
        Validate.isTrue(isPlayer());
        return (Player) sender;
    }

    public PlayerWrapper getWrappedPlayer() {
        return new PlayerWrapper(getPlayer());
    }

    public void sendMessage(Object... messages) {
        for (Object message : messages) {
            sender.sendMessage(MCUtils.colorize(String.valueOf(message)));
        }
    }

    public CommandSender getSender() {
        return sender;
    }
}
