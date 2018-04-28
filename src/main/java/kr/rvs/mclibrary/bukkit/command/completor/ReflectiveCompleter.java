package kr.rvs.mclibrary.bukkit.command.completor;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-26.
 */
public class ReflectiveCompleter implements TabCompletable {
    private final Object object;
    private final Method method;

    public ReflectiveCompleter(Object object, Method method) {
        Class<?> retType = method.getReturnType();
        Class<?>[] paramTypes = method.getParameterTypes();
        Validate.isTrue(
                List.class.isAssignableFrom(retType) &&
                        paramTypes.length == 2 &&
                        CommandSenderWrapper.class.isAssignableFrom(paramTypes[0]) &&
                        List.class.isAssignableFrom(paramTypes[1])
        );

        this.object = object;
        this.method = method;
        method.setAccessible(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) {
        List<String> ret = null;
        try {
            ret = (List<String>) method.invoke(object, wrapper, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Static.log(e);
        }
        return ret != null ? ret : new ArrayList<>();
    }
}
