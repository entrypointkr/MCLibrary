package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.collection.OptionalHashMap;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public class CompositeCommand extends OptionalHashMap<String, ICommand> implements ICommand {
    @Override
    public void execute(CommandSender sender, String label, CommandArguments args) {
        getOptional(args.pollFirst()).ifPresent(mcCommand ->
                mcCommand.execute(sender, label, args));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String label, CommandArguments args) {
        // Sub
        String firstArg = args.peekFirst();
        ICommand command = get(firstArg);
        if (command != null) {
            args.remove(0);
            return command.tabComplete(sender, label, args);
        }

        // Else
        return keySet().stream()
                .filter(key -> key.startsWith(firstArg))
                .collect(Collectors.toList());
    }

    @Override
    public CompositeCommand computeIfAbsent(String key, Function<? super String, ? extends ICommand> mappingFunction) {
        ICommand command = super.computeIfAbsent(key, mappingFunction);
        if (!(command instanceof CompositeCommand)) {
            Static.log(Level.WARNING, String.format(
                    "CompositeCommand expected, but find %s, key: %s",
                    command.toString(), key)
            );
            put(key, command = new CompositeCommand());
        }

        return (CompositeCommand) command;
    }

    public CompositeCommand computeIfAbsent(CommandArguments args, Function<String, CompositeCommand> function) {
        String firstArg = args.peekFirst();
        CompositeCommand composite = computeIfAbsent(firstArg, function);

        for (int i = 1; i < args.size(); i++) {
            String arg = args.get(i);
            composite = composite.computeIfAbsent(arg, k -> new CompositeCommand());
        }

        return composite;
    }
}
