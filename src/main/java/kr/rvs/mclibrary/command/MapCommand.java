package kr.rvs.mclibrary.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JunHyeong Lim on 2018-05-15
 */
public class MapCommand<T> implements ICommand<T> {
    private final EmptyCommand<T> DEFAULT = new EmptyCommand<>();
    protected final Map<String, ICommand<T>> map = new HashMap<>();

    @Override
    public void execute(T data, List<String> args) {
        if (!args.isEmpty()) {
            String arg = args.remove(0);
            ICommand<T> command = map.getOrDefault(arg, DEFAULT);
            command.execute(data, args);
        }
    }

    public Set<Map.Entry<String, ICommand<T>>> entrySet() {
        return map.entrySet();
    }

    public ICommand<T> get(String key) {
        return map.get(key);
    }

    public MapCommand<T> put(String key, ICommand<T> command) {
        map.put(key, command);
        return this;
    }

    public MapCommand<T> remove(String key) {
        map.remove(key);
        return this;
    }
}
