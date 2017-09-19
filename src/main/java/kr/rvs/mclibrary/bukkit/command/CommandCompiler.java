package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.internal.CompositeCommand;
import kr.rvs.mclibrary.bukkit.command.internal.ExecutableCommand;
import kr.rvs.mclibrary.bukkit.command.internal.ICommand;

import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public class CommandCompiler {
    private static final Pattern ARGS_PATTERN = Pattern.compile(" ", Pattern.LITERAL);
    private ICommand command;

    public void addCommand(SubCommand subCommand) {
        String[] args = ARGS_PATTERN.split(subCommand.args()); // /test a b c
        int lastIndex = args.length - 1;
        if (lastIndex >= 0 && args[lastIndex] != null) {
            CompositeCommand ctx = new CompositeCommand();
            ctx.put(args[lastIndex], new ExecutableCommand(subCommand));

            for (int i = lastIndex - 1; i >= 0; i--) {
                CompositeCommand newCtx = new CompositeCommand();
                newCtx.put(args[i], ctx);
                ctx = newCtx;
            }

            command = ctx;
        } else {
            command = new ExecutableCommand(subCommand);
        }
    }

    public ICommand getCommand() {
        return command;
    }
}
