package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.Static;

import java.util.HashMap;
import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * Created by Junhyeong Lim on 2017-09-26.
 */
public abstract class CompositeBase<VALUE, SUB extends CompositeBase<VALUE, SUB>> extends HashMap<String, VALUE> {
    @SuppressWarnings("unchecked")
    public SUB setupComposite(String[] args, int start, int end, Supplier<VALUE> def) {
        SUB ret = (SUB) this;
        for (int i = start; i < end; i++) {
            String arg = args[i];
            VALUE command = ret.computeIfAbsent(arg, k -> def.get());
            if (!(command instanceof CompositeBase)) {
                Static.log(Level.WARNING, String.format(
                        "CompositeBase expected, but find %s, key: %s",
                        command.toString(), arg
                ));
                ret.put(arg, command = def.get());
            }
            ret = (SUB) command;
        }
        return ret;
    }
}
