package kr.rvs.mclibrary.struct.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandProcessor extends Command {
    public CommandProcessor(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return true;
    }
}
