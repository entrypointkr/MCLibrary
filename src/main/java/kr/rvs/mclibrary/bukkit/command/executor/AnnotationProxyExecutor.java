package kr.rvs.mclibrary.bukkit.command.executor;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandInfo;
import kr.rvs.mclibrary.bukkit.command.CommandType;
import kr.rvs.mclibrary.bukkit.command.Executable;
import kr.rvs.mclibrary.bukkit.command.annotation.Command;
import kr.rvs.mclibrary.bukkit.command.exception.CommandException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
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
        if (StringUtils.isNotEmpty(perm) && !sender.hasPermission(perm)) {
            throw new PermissionDeniedException(wrapper, args, this, perm);
        }
        String message = null;
        if (annotation.min() > args.size())
            message = String.format("인자를 %d 개 이상 입력하세요. (%d 개 부족)",
                    annotation.min(), annotation.min() - args.size());
        else if (annotation.max() < args.size())
            message = String.format("인자를 %d 개 이하로 입력하세요. (%d 개 초과)",
                    annotation.max(), args.size() - annotation.max());
        else if (!annotation.type().isValid(sender))
            message = annotation.type() == CommandType.PLAYER ? "플레이어만 사용 가능합니다." :
                    annotation.type() == CommandType.CONSOLE ? "콘솔만 사용 가능합니다." : "사용할 수 없는 명령어입니다.";
        if (message != null) {
            throw new InvalidUsageException(wrapper, args, this, this, ChatColor.RED + message);
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
