package kr.rvs.mclibrary.bukkit.external;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;

/**
 * Created by Junhyeong Lim on 2017-10-31.
 */
public class Vaults {
    public static Permission getPermission() {
        return Bukkit.getServicesManager().load(Permission.class);
    }

    public static Economy getEconomy() {
        return Bukkit.getServicesManager().load(Economy.class);
    }

    public static Chat getChat() {
        return Bukkit.getServicesManager().load(Chat.class);
    }

    private Vaults() {
    }
}
