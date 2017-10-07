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
public abstract class Wizard<D, C> {
    private static final EntityHashMap<Wizard<?, ?>> wizardMap = new EntityHashMap<>();
    private final Player player;
    private Consumer<C> callback;
    private final D data;
    private final String startMessage;
    private final String completeMessage;

    public Wizard(Player player, D data, String startMessage, String completeMessage) {
        this.player = player;
        this.data = data;
        this.startMessage = MCUtils.colorize(startMessage);
        this.completeMessage = MCUtils.colorize(completeMessage);
    }

    public void start(Consumer<C> callback) throws Exception {
        Validate.isTrue(wizardMap.containsKey(player), "Already has a wizard");

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
        callback.accept(callbackData);
        wizardMap.remove(player);
        player.sendMessage(messageCaught(completeMessage));
    }

    public D getData() {
        return data;
    }

    public String getStartMessage() {
        return startMessage;
    }

    public String getCompleteMessage() {
        return completeMessage;
    }

    protected Player getPlayer() {
        return player;
    }

    protected void registerEvents(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, MCLibrary.getPlugin());
    }
}
