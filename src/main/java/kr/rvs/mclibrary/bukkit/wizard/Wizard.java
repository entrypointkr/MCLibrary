package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.collection.EntityHashMap;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-10-06.
 */
public abstract class Wizard<D, C> { // TODO: Rewrite
    protected static final EntityHashMap<Wizard<?, ?>> wizardMap = new EntityHashMap<>();
    protected final Player player;
    protected Consumer<C> callback;
    protected final D data;
    protected final String startMessage;
    protected final String completeMessage;

    public Wizard(Player player, D data, String startMessage, String completeMessage) {
        this.player = player;
        this.data = data;
        this.startMessage = MCUtils.colorize(startMessage);
        this.completeMessage = MCUtils.colorize(completeMessage);
    }

    public void start(Consumer<C> callback) throws Exception {
        Validate.isTrue(!wizardMap.containsKey(player), "Already has a wizard");

        this.callback = callback;

        player.sendMessage(messageCaught(startMessage));
        process(data);
        wizardMap.put(player, this);
    }

    public void start(Consumer<C> callback, CommandSender sender) {
        try {
            start(callback);
        } catch (Exception e) {
            sender.sendMessage("&c이미 진행중입니다.");
        }
    }

    protected abstract void process(D data);

    protected String messageCaught(String message) {
        return message; // Hook
    }

    protected void release(C callbackData) {
        wizardMap.remove(player);
        callback.accept(callbackData);
        player.sendMessage(messageCaught(completeMessage));
    }

    protected void registerEvents(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, MCLibrary.getPlugin());
    }
}
