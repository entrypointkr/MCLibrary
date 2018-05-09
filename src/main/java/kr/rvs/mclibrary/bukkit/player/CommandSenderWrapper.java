package kr.rvs.mclibrary.bukkit.player;

import kr.rvs.mclibrary.bukkit.Colors;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import org.bukkit.Material;
import org.bukkit.World;
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

    public Optional<Player> getPlayer() {
        return sender instanceof Player
                ? Optional.of((Player) sender)
                : Optional.empty();
    }

    public Player getPlayerOrThrow(String usage) {
        return getPlayer().orElseThrow(() ->
                new InvalidUsageException(this, usage));
    }

    public Player getPlayerOrThrow() {
        return getPlayerOrThrow("당신은 온라인 플레이어가 아닙니다.");
    }

    public PlayerWrapper getWrappedPlayer() {
        return new PlayerWrapper(getPlayerOrThrow());
    }

    @SuppressWarnings("deprecation")
    public Optional<ItemStack> getItemInHand() {
        return getPlayer().map(Player::getItemInHand).filter(item -> item.getType() != Material.AIR);
    }

    public ItemStack getItemInHandOrThrow(String usage) {
        return getItemInHand().orElseThrow(() -> new InvalidUsageException(this, usage));
    }

    public ItemStack getItemInHandOrThrow() {
        return getItemInHandOrThrow("손에 아이템을 들어주세요.");
    }

    public Optional<World> getWorld() {
        return getPlayer().map(Player::getWorld);
    }

    public World getWorldOrThrow(String usage) {
        return getWorld().orElseThrow(() -> new InvalidUsageException(usage));
    }

    public World getWorldOrThrow() {
        return getWorldOrThrow("존재하지 않는 월드입니다.");
    }

    public void sendMessage(CharSequence... messages) {
        for (CharSequence message : messages) {
            sender.sendMessage(Colors.colorize(message.toString()));
        }
    }

    public void sendMessage(Object... messages) {
        for (Object message : messages) {
            sender.sendMessage(Colors.colorize(String.valueOf(message)));
        }
    }

    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    public CommandSenderWrapper checkPermission(String permission) {
        if (!hasPermission(permission))
            throw new PermissionDeniedException(this, permission);
        return this;
    }

    public String getName() {
        return sender.getName();
    }

    public CommandSender getSender() {
        return sender;
    }
}
