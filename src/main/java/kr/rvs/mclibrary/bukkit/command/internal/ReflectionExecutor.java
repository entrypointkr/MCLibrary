package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import kr.rvs.mclibrary.general.MethodWrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public class ReflectionExecutor extends MethodWrapper implements CommandExecutable {
    public ReflectionExecutor(Object handle, Method method) {
        super(handle, method);
    }

    @Override
    public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
        try {
            getMethod().invoke(getHandle(), sender, label, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Static.log(e);
        }
    }
}
