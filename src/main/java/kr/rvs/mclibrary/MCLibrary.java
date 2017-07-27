package kr.rvs.mclibrary;

import kr.rvs.mclibrary.util.bukkit.command.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MCLibrary extends JavaPlugin {
    public static MCLibrary self;
    private static final CommandManager manager = new CommandManager();

    public MCLibrary() {
        self = this;
    }

    @Override
    public void onEnable() {

    }

    public static CommandManager getCommandManager() {
        return manager;
    }
}
