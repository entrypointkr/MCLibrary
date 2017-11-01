package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.MCLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-11-01.
 */
public abstract class ListenerWizard<C> extends Wizard<C> {
    private Listener listener;

    public ListenerWizard(Player player, Consumer<C> callback) {
        super(player, callback);
    }

    protected void registerListener(Listener listener) {
        this.listener = listener;
        Bukkit.getPluginManager().registerEvents(listener, MCLibrary.getPlugin());
    }

    @Override
    protected void release() {
        if (listener != null)
            HandlerList.unregisterAll(listener);
    }
}
