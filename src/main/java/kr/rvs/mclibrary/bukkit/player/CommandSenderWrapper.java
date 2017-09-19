package kr.rvs.mclibrary.bukkit.player;

import kr.rvs.mclibrary.bukkit.MCUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-09-17.
 */
public class CommandSenderWrapper {
    private final CommandSender sender;

    public CommandSenderWrapper(CommandSender sender) {
        this.sender = sender;
    }

    public Player getPlayer() {
        Validate.isTrue(sender instanceof Player);
        return (Player) sender;
    }

    public void sendMessage(String... messages) {
        for (String message : messages) {
            sender.sendMessage(MCUtils.colorize(message));
        }
    }

    public CommandSender getSender() {
        return sender;
    }
}
