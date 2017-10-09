package kr.rvs.mclibrary.bukkit.storage;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class SoundStorage {
    private Sound sound;
    private SoundCategory category;
    private float volume;
    private float pitch;

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
        if (location != null)
            location.getWorld().playSound(location, sound, category, volume, pitch);
    }

    public void play(Player player, Location location) {
        if (player != null)
            player.playSound(location, sound, category, volume, pitch);
    }

    public void play(Player player) {
        if (player != null)
            play(player, player.getLocation());
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public SoundCategory getCategory() {
        return category;
    }

    public void setCategory(SoundCategory category) {
        this.category = category;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
