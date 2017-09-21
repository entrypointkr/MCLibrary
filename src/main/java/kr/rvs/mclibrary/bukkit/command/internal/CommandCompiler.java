package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import kr.rvs.mclibrary.bukkit.command.SubCommand;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-09-21.
 */
public class CommandCompiler {
    private static final Pattern ARGS_PATTERN = Pattern.compile(" ", Pattern.LITERAL);
    private final BaseCommand baseCommand;

    public CommandCompiler(BaseCommand baseCommand) {
        this.baseCommand = baseCommand;
    }

    public ICommand compile() {
        CompositeCommand ret = new CompositeCommand();
        SubCommand[] subCommands = baseCommand.commands();

        for (SubCommand command : subCommands) {
            CommandArguments args = new CommandArguments(Arrays.asList(ARGS_PATTERN.split(command.args())));
            int lastIndex = args.size() - 1;
            String lastArg = lastIndex >= 0 && args.get(0) != null ? args.remove(lastIndex--) : "";
            CompositeCommand ctx = ret;

            for (int i = 0; i <= lastIndex; i++) {
                String arg = args.get(i);
                ctx = ctx.computeIfAbsent(arg, k -> new CompositeCommand());
            }

            SubCommandProxy proxy = new SubCommandProxy(command);
            ctx.put(lastArg, new ExecutableCommand(proxy, proxy));
        }

        return ret;
    }
}
