package kr.rvs.mclibrary.util.bukkit.general;

import kr.rvs.mclibrary.util.bukkit.MCValidate;
import kr.rvs.mclibrary.util.bukkit.player.PlayerUtils;
import me.confuser.barapi.BarAPI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-07-28.
 */
public class LegacyBossBar {
    private final Set<Player> receiver = new HashSet<>();
    private String text = "";
    private float healthPercent = 100;
    private int timeout = -1;

    public LegacyBossBar setText(String text) {
        this.text = text;
        return this;
    }

    public LegacyBossBar setHealthPercent(float healthPercent) {
        this.healthPercent = healthPercent;
        return this;
    }

    public LegacyBossBar setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public LegacyBossBar addPlayer(Player... players) {
        receiver.addAll(Arrays.asList(players));
        return this;
    }

    public LegacyBossBar removePlayer(Player... players) {
        for (Player player : players) {
            BarAPI.removeBar(player);
            receiver.remove(player);
        }
        return this;
    }

    public LegacyBossBar show(ShowType type) {
        MCValidate.isBarAPIEnabled();

        Collection<? extends Player> players;
        if (type == ShowType.GLOBAL) {
            players = PlayerUtils.getOnlinePlayers();
        } else {
            players = receiver;
        }

        for (Player player : players) {
            showToSpecificPlayer(player);
        }

        return this;
    }

    public LegacyBossBar showToSpecificPlayer(Player player) {
        if (timeout != -1) {
            BarAPI.setMessage(player, text, timeout);
        } else {
            BarAPI.setMessage(player, text, healthPercent);
        }
        return this;
    }

    public enum ShowType {
        GLOBAL,
        LOCAL
    }
}
