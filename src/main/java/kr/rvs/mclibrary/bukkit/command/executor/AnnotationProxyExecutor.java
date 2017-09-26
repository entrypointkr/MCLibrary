package kr.rvs.mclibrary.bukkit.command.executor;

import kr.rvs.mclibrary.bukkit.command.Command;
import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.command.exception.CommandException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class AnnotationProxyExecutor implements CommandExecutable {
    private final Command annotation;
    private final CommandExecutable executable;

    public AnnotationProxyExecutor(Command annotation, CommandExecutable executable) {
        this.annotation = annotation;
        this.executable = executable;
    }

    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) throws CommandException {
        CommandSender sender = wrapper.getSender();
        String perm = annotation.perm();
        if (!(annotation.min() <= args.size() &&
                annotation.max() >= args.size() &&
                annotation.type().isValid(sender))) {
            throw new InvalidUsageException(this);
        }
        if (StringUtils.isNotEmpty(perm) && !sender.hasPermission(perm)) {
            throw new PermissionDeniedException(this);
        }
        executable.execute(wrapper, args);
    }
}
