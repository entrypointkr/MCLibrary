package kr.rvs.mclibrary.bukkit.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public abstract class BaseCommand {
    private final CommandCompiler compiler = new CommandCompiler();

    public abstract String label();

    public String description() {
        return "MCLibrary command";
    }

    public String usage() {
        return '/' + label();
    }

    public List<String> aliases() {
        return new ArrayList<>();
    }

    public BaseCommand addCommand(SubCommand... commands) {
        for (SubCommand command : commands) {
            compiler.addCommand(command);
        }
        return this;
    }

    public CommandCompiler getCompiler() {
        return compiler;
    }
}
