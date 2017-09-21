package kr.rvs.mclibrary.bukkit.command.internal;

import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import kr.rvs.mclibrary.general.MethodWrapper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
public class ReflectionTabCompletor extends MethodWrapper implements TabCompletable {
    public ReflectionTabCompletor(Object handle, Method method) {
        super(handle, method);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> tabComplete(CommandSenderWrapper sender, String label, VolatileArrayList args) throws IllegalArgumentException {
        List<String> ret = new ArrayList<>();

        try {
            Collection<String> completes = (Collection<String>) getMethod().invoke(get(), sender, label, args);
            ret.addAll(completes);
        } catch (Exception ignore) {

        }

        return ret;
    }
}
