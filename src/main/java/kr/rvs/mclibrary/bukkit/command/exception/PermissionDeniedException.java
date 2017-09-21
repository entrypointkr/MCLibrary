package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-21.
 */
public class PermissionDeniedException extends CommandException {
    private final String permissionNode;

    public PermissionDeniedException(BaseCommand command, CommandSenderWrapper sender, String permissionNode) {
        super(command, sender);
        this.permissionNode = permissionNode;
    }

    public String getPermissionNode() {
        return permissionNode;
    }
}
