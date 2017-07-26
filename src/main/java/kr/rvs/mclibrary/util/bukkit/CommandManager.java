package kr.rvs.mclibrary.util.bukkit;

import kr.rvs.mclibrary.struct.command.CommandProcessor;
import kr.rvs.mclibrary.struct.command.MCCommand;
import kr.rvs.mclibrary.util.Static;
import kr.rvs.mclibrary.util.reflection.ClassProbe;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandManager {
    public void registerCommand(MCCommand command) {
        CommandStorageOld storage = new CommandStorageOld(command);

        CommandProcessor processor = new CommandProcessor(
                command.label(),
                command.description(),
                command.usage(),
                Arrays.asList(command.aliases()),
                storage
        );

        CommandUtils.registerCommand(processor.getLabel(), processor);
    }

    public void registerCommand(String packageName) {
        ClassProbe probe = new ClassProbe(packageName);
        List<Class<? extends MCCommand>> classes = probe.getSubTypesOf(MCCommand.class);

        classes.forEach(aClass -> {
            try {
                registerCommand(aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                Static.log(e);
            }
        });
    }
}
