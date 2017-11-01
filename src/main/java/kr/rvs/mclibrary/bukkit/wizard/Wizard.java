package kr.rvs.mclibrary.bukkit.wizard;

import kr.rvs.mclibrary.bukkit.collection.EntityHashMap;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-11-01.
 */
public abstract class Wizard<C> {
    private static final EntityHashMap<Wizard<?>> WIZARD_MAP = new EntityHashMap<>();
    private final Player player;
    private final Consumer<C> callback;

    public Wizard(Player player, Consumer<C> callback) {
        this.player = player;
        this.callback = callback;
    }

    protected Player getPlayer() {
        return player;
    }

    protected Consumer<C> getCallback() {
        return callback;
    }

    protected abstract void process();

    protected abstract void release();

    protected void release(C data) {
        release();
        getCallback().accept(data);
    }

    public void start(String message) {
        Player player = getPlayer();
        Optional.ofNullable(WIZARD_MAP.get(player)).ifPresent(Wizard::release);
        WIZARD_MAP.put(player, this);

        process();
        player.sendMessage(message);
    }

    public void start() {
        start("시작되었습니다.");
    }
}
