package kr.rvs.mclibrary.gson;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-08-19.
 */
public class SettingManager {
    private final Set<Settings> settings = new HashSet<>();

    public void add(Settings settings) {
        this.settings.add(settings);
    }

    public boolean contains(Settings settings) {
        return this.settings.contains(settings);
    }

    public void save() {
        settings.forEach(Settings::save);
    }
}
