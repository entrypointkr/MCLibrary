package kr.rvs.mclibrary.bukkit.command.executor;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.apache.commons.lang.Validate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
public class ReflectiveExecutor implements CommandExecutable {
    private final Object object;
    private final Method method;

    public ReflectiveExecutor(Object object, Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Validate.isTrue(
                        paramTypes.length == 2 &&
                        CommandSenderWrapper.class.isAssignableFrom(paramTypes[0]) &&
                        List.class.isAssignableFrom(paramTypes[1])
        );

        this.object = object;
        this.method = method;
        method.setAccessible(true);
    }

    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
        try {
            method.invoke(object, wrapper, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Static.log(e);
        }
    }
}
