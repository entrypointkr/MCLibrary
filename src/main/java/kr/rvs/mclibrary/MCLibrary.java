package kr.rvs.mclibrary;

import kr.rvs.mclibrary.util.bukkit.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MCLibrary extends JavaPlugin {
    private static final CommandManager manager = new CommandManager();

    @Override
    public void onEnable() {

    }

    public static CommandManager getManager() {
        return manager;
    }
}
