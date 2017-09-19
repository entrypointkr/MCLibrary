package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public class ReflectionExecutable implements CommandExecutable {
    private final Object object;
    private final Method method;

    public ReflectionExecutable(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    @Override
    public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
        try {
            method.invoke(object, sender, label, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Static.log(e);
        }
    }
}
