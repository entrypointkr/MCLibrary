package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public class HelpSubCommand implements SubCommand {
    private final SubCommand[] subCommands;
    private final String args;
    private int line = 8;

    public HelpSubCommand(SubCommand[] subCommands, String args) {
        this.subCommands = subCommands;
        this.args = args;
    }

    public HelpSubCommand(SubCommand[] subCommands, BaseCommand baseCommand) {
        this(subCommands, StringUtils.isAlphanumeric(baseCommand.getLabel()) ? "help" : "도움말");
    }

    @Override
    public String args() {
        return args;
    }

    @Override
    public void execute(CommandSenderWrapper wrapper, String label, VolatileArrayList args) {
        CommandSender sender = wrapper.getSender();
        StringBuilder header = new StringBuilder()
                .append("&e--------- ").append("&f도움말: /").append(label).append(' ');

        if (sender instanceof ConsoleCommandSender) {
            header.append("&e");
            headerFill(header);
            wrapper.sendMessage(header);

            for (SubCommand command : subCommands) {
                wrapper.sendMessage(commandUsage(label, command));
            }
        } else {
            int currPage = Math.max(args.getInt(0, 1), 1);
            int start = (currPage - 1) * line;
            int end = subCommands.length;
            int maxPage = end / line + end % line > 0 ? 1 : 0;

            header.append(String.format("(%d/%d)", currPage, maxPage))
                    .append("&e");
            headerFill(header);

            wrapper.sendMessage(header);
            wrapper.sendMessage(String.format("&7'/%s %s [페이지]' 를 입력할 수 있습니다.", label, this.args));

            for (int i = start; i < end; i++) {
                wrapper.sendMessage(commandUsage(label, subCommands[i]));
            }
        }
    }

    private void headerFill(StringBuilder builder) {
        for (int i = builder.length(); i < 55; i++) {
            builder.append('-');
        }
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

    public void setLine(int line) {
        this.line = line;
    }
}
