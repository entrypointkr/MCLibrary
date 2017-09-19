package kr.rvs.mclibrary.bukkit.command.internal;

import org.bukkit.command.CommandSender;

import java.util.Queue;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public interface ICommand {
    void execute(CommandSender sender, String label, Queue<String> args);
}
