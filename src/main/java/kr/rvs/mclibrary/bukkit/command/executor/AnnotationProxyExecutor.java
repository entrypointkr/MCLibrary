package kr.rvs.mclibrary.bukkit.command.executor;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandInfo;
import kr.rvs.mclibrary.bukkit.command.Executable;
import kr.rvs.mclibrary.bukkit.command.annotation.Command;
import kr.rvs.mclibrary.bukkit.command.exception.CommandException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

/**
 * Created by Junhyeong Lim on 2017-09-27.
 */
public class AnnotationProxyExecutor implements Executable, CommandInfo {
    private final Command annotation;
    private final Executable executable;

    public AnnotationProxyExecutor(Command annotation, Executable executable) {
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
            throw new InvalidUsageException(wrapper, args, this, this);
        }
        if (StringUtils.isNotEmpty(perm) && !sender.hasPermission(perm)) {
            throw new PermissionDeniedException(wrapper, args, this, perm);
        }
        executable.execute(wrapper, args);
    }

    @Override
    public String usage() {
        return annotation.usage();
    }

    @Override
    public String desc() {
        return annotation.desc();
    }

    @Override
    public String perm() {
        return annotation.perm();
    }
}
