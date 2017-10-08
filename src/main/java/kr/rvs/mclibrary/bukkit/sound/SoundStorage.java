package kr.rvs.mclibrary.bukkit.sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class SoundStorage {
    private final Sound sound;
    private final SoundCategory category;
    private final float volume;
    private final float pitch;

    public SoundStorage(Sound sound, SoundCategory category, float volume, float pitch) {
        this.sound = sound;
        this.category = category;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SoundStorage(Sound sound, float volume, float pitch) {
        this(sound, SoundCategory.MASTER, volume, pitch);
    }

    public void play(Location location) {
        location.getWorld().playSound(location, sound, category, volume, pitch);
    }

    public void play(Player player, Location location) {
        player.playSound(location, sound, category, volume, pitch);
    }

    public void play(Player player) {
        play(player, player.getLocation());
    }
}
