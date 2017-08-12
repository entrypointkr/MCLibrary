package kr.rvs.mclibrary.struct.command.layout;

import kr.rvs.mclibrary.struct.command.CommandStorage;
import kr.rvs.mclibrary.struct.command.MCCommand;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-08-12.
 */
public class CommandLayoutStorage {
    private final StringBuilder builder;
    private final CommandSender sender;
    private final MCCommand parent;
    private final List<CommandStorage> storages;
    private final VolatileArrayList args;

    public CommandLayoutStorage(StringBuilder builder, CommandSender sender, MCCommand parent, List<CommandStorage> storages, VolatileArrayList args) {
        this.builder = builder;
        this.sender = sender;
        this.parent = parent;
        this.storages = new ArrayList<>(storages);
        this.args = args;
    }

    public StringBuilder append(CharSequence charSequence) {
        return builder.append(charSequence);
    }

    public StringBuilder append(char ch) {
        return builder.append(ch);
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    public CommandSender getSender() {
        return sender;
    }

    public MCCommand getParent() {
        return parent;
    }

    public List<CommandStorage> getStorages() {
        return storages;
    }

    public VolatileArrayList getArgs() {
        return args;
    }
}