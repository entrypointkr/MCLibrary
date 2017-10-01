package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.duplex.HelpExecutor;
import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;

/**
 * Created by Junhyeong Lim on 2017-09-28.
 */
public class DefaultExceptionHandler implements CommandExceptionHandler {
    private HelpExecutor helpExecutor;

    @Override
    public void init(CommandAdaptor adaptor) {
        ICommand command = adaptor.getCommand();
        this.helpExecutor = new HelpExecutor(command, adaptor.getLabel(), 8);
    }

    @Override
    public void handle(CommandNotFoundException ex) {
        CommandSenderWrapper wrapper = ex.getWrapper();
        CommandArguments args = ex.getArguments();
        helpExecutor.execute(wrapper, args);
    }

    @Override
    public void handle(InvalidUsageException ex) {
        CommandSenderWrapper wrapper = ex.getWrapper();
        CommandArguments arguments = ex.getArguments();
        CommandInfo commandInfo = ex.getCommandInfo();
        wrapper.sendMessage(ex.getMessage());
        helpExecutor.sendCommandInfo(wrapper, arguments.getConsumedArgs(), commandInfo);
    }

    @Override
    public void handle(PermissionDeniedException ex) {
        CommandSenderWrapper wrapper = ex.getWrapper();
        String permission = ex.getPermission();
        wrapper.sendMessage(String.format("&c권한 &e%s &c이 없습니다.", permission));
    }

    @Override
    public void handle(Exception ex) {

    }
}
