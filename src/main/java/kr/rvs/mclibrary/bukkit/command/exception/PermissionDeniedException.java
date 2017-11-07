package kr.rvs.mclibrary.bukkit.command.exception;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class PermissionDeniedException extends CommandException {
    private final String permission;

    public PermissionDeniedException(Object source, String permission) {
        super(source);
        this.permission = permission;
    }

    public PermissionDeniedException(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
