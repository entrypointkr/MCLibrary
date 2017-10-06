package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.collection.EntityHashMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public abstract class Wizard<D> {
    private static final EntityHashMap<Wizard<?>> wizardMap = new EntityHashMap<>();
    private final Player player;

    public Wizard(Player player) {
        this.player = player;
    }

    public void start(Consumer<D> callback) throws WizardException {
        if (wizardMap.containsKey(player))
            throw new WizardException("Already has a wizard");

        wizardMap.put(player, this);
        start0(callback);
    }

    public void start(Consumer<D> callback, CommandSender sender) {
        try {
            start(callback);
        } catch (WizardException e) {
            sender.sendMessage("&c이미 진행중입니다.");
        }
    }

    protected abstract void start0(Consumer<D> callback);

    protected Player getPlayer() {
        return player;
    }

    protected void registerEvents(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, MCLibrary.getPlugin());
    }

    protected void release(HandlerList handlerList, Listener listener) {
        handlerList.unregister(listener);
        wizardMap.remove(player);
    }
}
