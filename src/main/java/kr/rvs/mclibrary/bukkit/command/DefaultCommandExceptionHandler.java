package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.exception.CommandNotFoundException;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Junhyeong Lim on 2017-09-21.
 */
public class DefaultCommandExceptionHandler extends CommandExceptionHandlerAdapter {
    @Override
    public void handle(CommandNotFoundException ex) {
        CommandSenderWrapper wrapper = ex.getSender();
        BaseCommand baseCommand = ex.getCommand();
        HelpSubCommand helpSubCommand = null;
        for (SubCommand command : baseCommand.commands()) {
            if (command instanceof HelpSubCommand) {
                helpSubCommand = (HelpSubCommand) command;
                break;
            }
        }

        if (helpSubCommand != null) {
            helpSubCommand.execute(wrapper, baseCommand, baseCommand.getLabel(), new VolatileArrayList());
        } else {
            StringBuilder header = new StringBuilder()
                    .append("&e--------- ").append("&f도움말: /").append(baseCommand.getLabel()).append(" &e");
            for (int i = header.length(); i < 55; i++) {
                header.append('-');
            }
            wrapper.sendMessage(header.toString());

            for (SubCommand command : baseCommand.commands()) {
                wrapper.sendMessage(commandUsage(baseCommand.getLabel(), command));
            }
        }
    }

    @Override
    public void handle(InvalidUsageException ex) {
        CommandSenderWrapper wrapper = ex.getSender();
        wrapper.sendMessage("&c명령어를 잘못 사용하셨습니다. 올바른 사용법:");
        wrapper.sendMessage(commandUsage(ex.getCommand().getLabel(), ex.getSubCommand()));
    }

    @Override
    public void handle(PermissionDeniedException ex) {
        CommandSenderWrapper wrapper = ex.getSender();
        wrapper.sendMessage("&c권한 &f" + ex.getPermissionNode() + " &c이 필요합니다.");
    }

    private String commandUsage(String label, SubCommand command) {
        StringBuilder contents = new StringBuilder()
                .append("&6/").append(label);
        if (StringUtils.isNotEmpty(command.args()))
            contents.append(' ').append(command.args());
        if (StringUtils.isNotEmpty(command.usage()))
            contents.append(' ').append(command.usage());
        if (StringUtils.isNotEmpty(command.desc())) {
            contents.append(": &f");
            contents.append(' ').append(command.desc());
        }
        return contents.toString();
    }
}
