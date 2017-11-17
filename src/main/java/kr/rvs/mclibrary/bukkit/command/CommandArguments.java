package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.command.exception.PermissionDeniedException;
import kr.rvs.mclibrary.bukkit.player.PlayerUtils;
import kr.rvs.mclibrary.bukkit.world.WorldUtils;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;

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

    @Override
    public String get(int index, String def) {
        return getOptional(index).orElse(def);
    }

    public String getStrings(int start, int end, String separator) {
        return StringUtils.join(subList(start, Math.min(end, size())), separator);
    }

    public String getStrings(int start, int end) {
        return getStrings(start, end, " ");
    }

    public String getStrings(int start) {
        return getStrings(start, size(), " ");
    }

    public Integer getInt(int index, String usage) {
        return getInt(index).orElseThrow(() -> new InvalidUsageException(usage));
    }

    public Double getDouble(int index, String usage) {
        return getDouble(index).orElseThrow(() -> new InvalidUsageException(usage));
    }

    public Float getFloat(int index, String usage) {
        return getFloat(index).orElseThrow(() -> new InvalidUsageException(usage));
    }

    public Long getLong(int index, String usage) {
        return getLong(index).orElseThrow(() -> new InvalidUsageException(usage));
    }

    public Optional<World> getWorld(int index) {
        return getOptional(index).flatMap(WorldUtils::getWorld);
    }

    public World getWorldOrThrow(int index, String usage) {
        return getWorld(index).orElseThrow(() -> new InvalidUsageException(usage));
    }

    public World getWorldOrThrow(int index) {
        return getWorldOrThrow(index, "존재하지 않는 월드입니다.");
    }

    public Optional<Player> getPlayer(int index) {
        return getOptional(index).flatMap(PlayerUtils::getPlayer);
    }

    public Player getPlayerOrThrow(int index, String usage) {
        return getPlayer(index).orElseThrow(() -> new InvalidUsageException(usage));
    }

    public Player getPlayerOrThrow(int index) {
        return getPlayerOrThrow(index, "온라인 중인 플레이어가 아닙니다.");
    }

    public Player getPlayerWithPermission(int index, String permission) {
        return Optional.of(getPlayerOrThrow(index)).filter(p -> p.hasPermission(permission)).orElseThrow(() ->
                new PermissionDeniedException(permission));
    }

    public CommandArguments filter(boolean condition, String usage) {
        if (!condition)
            throw new InvalidUsageException(usage);
        return this;
    }

    public CommandInfo getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(CommandInfo lastCommand) {
        this.lastCommand = lastCommand;
    }
}
