package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.SubCommand;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public class SubCommandProxy implements SubCommand, TabCompletable {
    private final SubCommand command;

    public SubCommandProxy(SubCommand command) {
        this.command = command;
    }

    @Override
    public String args() {
        return command.args();
    }

    @Override
    public String perm() {
        return command.perm();
    }

    @Override
    public int min() {
        return command.min();
    }

    @Override
    public int max() {
        return command.max();
    }

    @Override
    public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
        if (command.min() > args.size() || command.max() < args.size()
                || !command.type().isValid(sender))
            return;

        String perm = command.perm();
        if (!StringUtils.isEmpty(perm) && !sender.getSender().hasPermission(perm)) {
            return;
        }

        command.execute(sender, label, args);
    }

    @Override
    public List<String> tabComplete(CommandSenderWrapper sender, String label, VolatileArrayList args) throws IllegalArgumentException {
        return command.tabComplete(sender, label, args);
    }
}
