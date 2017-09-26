package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.Static;

import java.util.HashMap;
import java.util.logging.Level;

/**
 * Created by Junhyeong Lim on 2017-09-26.
 */
public class CompositeBase<V, S extends CompositeBase<V, S>> extends HashMap<String, V> {
    @SuppressWarnings("unchecked")
    public S setupComposite(String[] args, int start, int end, V def) {
        S ret = (S) this;
        for (int i = start; i < end; i++) {
            String arg = args[i];
            V command = ret.computeIfAbsent(arg, k -> def);
            if (!(command instanceof CompositeBase)) {
                Static.log(Level.WARNING, String.format(
                        "CompositeBase expected, but find %s, key: %s",
                        command.toString(), arg
                ));
                ret.put(arg, command = def);
            }
            ret = (S) command;
        }
        return ret;
    }
}
