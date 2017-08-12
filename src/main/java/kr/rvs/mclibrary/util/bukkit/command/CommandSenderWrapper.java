package kr.rvs.mclibrary.util.bukkit.command;

import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-08-13.
 */
public class CommandSenderWrapper implements CommandSender {
    private final CommandSender handle;

    public CommandSenderWrapper(CommandSender handle) {
        this.handle = handle;
    }

    public Player getPlayer() {
        Validate.isTrue(handle instanceof Player);

        return (Player) handle;
    }

    @Override
    public void sendMessage(String message) {
        handle.sendMessage(message);
    }

    @Override
    public void sendMessage(String[] messages) {
        handle.sendMessage(messages);
    }

    @Override
    public Server getServer() {
        return handle.getServer();
    }

    @Override
    public String getName() {
        return handle.getName();
    }

    @Override
    public Spigot spigot() {
        return handle.spigot();
    }

    @Override
    public boolean isPermissionSet(String name) {
        return handle.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return handle.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(String name) {
        return handle.hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return handle.hasPermission(perm);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return handle.addAttachment(plugin, name, value);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return handle.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return handle.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return handle.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        handle.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        handle.recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return handle.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return handle.isOp();
    }

    @Override
    public void setOp(boolean value) {
        handle.setOp(value);
    }
}
