package kr.rvs.mclibrary.bukkit.command.duplex;

import kr.rvs.mclibrary.bukkit.command.CommandInformation;
import kr.rvs.mclibrary.bukkit.command.ICommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Junhyeong Lim on 2017-10-05.
 */
public class LegacyHelpExecutor extends AbstractHelpExecutor {
    public LegacyHelpExecutor(ICommand command, String label, int line) {
        super(command, label, line);
    }

    @Override
    public void sendCommandInfo(CommandSenderWrapper wrapper, String args, CommandInformation commandInformation) {
        CommandSender sender = wrapper.getSender();
        String usage = commandInformation.usage();
        boolean hasPerm = hasPerm(commandInformation, sender);
        ChatColor color = hasPerm ? ChatColor.GOLD : ChatColor.RED;
        String desc = commandInformation.desc();
        StringBuilder builder = new StringBuilder()
                .append(color).append('/').append(getLabel());

        if (StringUtils.isNotEmpty(args))
            builder.append(' ').append(args);
        if (StringUtils.isNotEmpty(usage))
            builder.append(' ').append(usage);
        if (StringUtils.isNotEmpty(desc))
            builder.append(": ").append(ChatColor.WHITE).append(desc);

        wrapper.sendMessage(builder);
    }
}
