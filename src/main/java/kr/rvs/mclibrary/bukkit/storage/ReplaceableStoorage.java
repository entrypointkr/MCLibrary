package kr.rvs.mclibrary.bukkit.storage;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.general.VarargsParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-10-09.
 */
public abstract class ReplaceableStoorage<S extends ReplaceableStoorage<?>> {
    private transient Map<String, String> replacements = new HashMap<>();

    private Map<String, String> getReplacements() {
        return replacements != null ? replacements : (replacements = new HashMap<>());
    }

    protected String formatting(String content) {
        for (Map.Entry<String, String> entry : getReplacements().entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        return MCUtils.colorize(content);
    }

    @SuppressWarnings("unchecked")
    public S replacement(Object... args) {
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section -> getReplacements().put(section.getString(0), section.getString(1)));
        return (S) this;
    }
}
