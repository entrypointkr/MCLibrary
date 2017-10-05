package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class CommandArguments extends VolatileArrayList { // TODO: Implement List, Deque
    private final StringBuilder consumedArgs = new StringBuilder();
    private CommandInfo lastCommand = CommandInfo.DEFAULT;

    public CommandArguments(int initialCapacity) {
        super(initialCapacity);
    }

    public CommandArguments() {
    }

    public CommandArguments(Collection<? extends String> c) {
        super(c);
    }

    @Override
    public String remove(int index) {
        String old = super.remove(index);
        if (StringUtils.isNotEmpty(old)) {
            if (consumedArgs.length() > 0)
                consumedArgs.append(' ');
            consumedArgs.append(old);
        }
        return old;
    }

    public String getConsumedArgs() {
        return consumedArgs.toString();
    }

    public String toPlainArgs() {
        StringBuilder builder = new StringBuilder();
        for (String arg : this) {
            if (builder.length() > 0)
                builder.append(' ');
            builder.append(arg);
        }
        return builder.toString();
    }

    public Optional<World> getWorld(int index) {
        String worldName = get(index, null);
        return StringUtils.isNotEmpty(worldName) ?
                Optional.ofNullable(Bukkit.getWorld(worldName)) :
                Optional.empty();
    }

    public CommandInfo getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(CommandInfo lastCommand) {
        this.lastCommand = lastCommand;
    }
}
