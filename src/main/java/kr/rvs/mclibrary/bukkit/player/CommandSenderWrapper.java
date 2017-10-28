package kr.rvs.mclibrary.bukkit.player;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

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
        return sender instanceof Player ?
                (Player) sender : null;
    }

    public Player getPlayerWithThrow(String usage) {
        return Optional.ofNullable(getPlayer()).orElseThrow(() ->
                new InvalidUsageException(this, usage));
    }

    public Optional<Player> getPlayerOptional() {
        return Optional.ofNullable(getPlayer());
    }

    public PlayerWrapper getWrappedPlayer() {
        return new PlayerWrapper(getPlayer());
    }

    public ItemStack getItemInHand() {
        ItemStack ret = null;
        Player player = getPlayer();
        if (player != null) {
            ret = player.getItemInHand();
        }
        return ret;
    }

    public ItemStack getItemInHandWithThrow(String usage) {
        return Optional.ofNullable(getItemInHand()).orElseThrow(() ->
                new InvalidUsageException(this, usage));
    }

    public void sendMessage(CharSequence... messages) {
        for (CharSequence message : messages) {
            sender.sendMessage(MCUtils.colorize(message.toString()));
        }
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
