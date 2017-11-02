package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.duplex.AbstractHelpExecutor;
import kr.rvs.mclibrary.bukkit.command.duplex.LegacyHelpExecutor;
import kr.rvs.mclibrary.bukkit.command.duplex.ModernHelpExecutor;
import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.general.Version;

/**
 * Created by Junhyeong Lim on 2017-09-28.
 */
public class DefaultExceptionHandler implements CommandExceptionHandler {
    private AbstractHelpExecutor helpExecutor;

    @Override
    public void init(CommandAdaptor adaptor) {
        ICommand command = adaptor.getCommand();
        this.helpExecutor = Version.BUKKIT.afterEquals(Version.V1_8) ?
                new ModernHelpExecutor(command, adaptor.getLabel(), 8) :
                new LegacyHelpExecutor(command, adaptor.getLabel(), 8);
    }

    @Override
    public void handle(Exception ex, CommandSenderWrapper wrapper, CommandArguments args) {
        if (ex instanceof CommandNotFoundException) {
            helpExecutor.execute(wrapper, args);
        } else if (ex instanceof InvalidUsageException) {
            wrapper.sendMessage(((InvalidUsageException) ex).getUsage());
            helpExecutor.sendCommandInfo(wrapper, args.getConsumedArgs(), args.getLastCommand());
        } else if (ex instanceof PermissionDeniedException) {
            String permission = ((PermissionDeniedException) ex).getPermission();
            wrapper.sendMessage(String.format("&c권한 &f%s &c이(가) 없습니다.", permission));
        }
    }
}
