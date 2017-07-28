package kr.rvs.mclibrary;

import kr.rvs.mclibrary.util.bukkit.command.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MCLibrary extends JavaPlugin {
    private static final CommandManager manager = new CommandManager();
    public static MCLibrary self;

    public MCLibrary() {
        self = this;
    }

    public static CommandManager getCommandManager() {
        return manager;
    }

    @Override
    public void onEnable() {

    }
}
