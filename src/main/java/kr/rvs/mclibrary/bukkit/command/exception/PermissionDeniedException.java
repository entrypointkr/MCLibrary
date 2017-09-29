package kr.rvs.mclibrary.bukkit.command.exception;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class PermissionDeniedException extends CommandException {
    private final String permission;

    public PermissionDeniedException(CommandSenderWrapper wrapper, CommandArguments arguments, CommandExecutable source, String permission) {
        super(wrapper, arguments, source);
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
