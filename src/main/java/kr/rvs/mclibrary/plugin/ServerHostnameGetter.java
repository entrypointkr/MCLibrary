package kr.rvs.mclibrary.plugin;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.Static;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Junhyeong Lim on 2017-10-04.
 */
public class ServerHostnameGetter implements Listener {
    public static void init(MCLibrary plugin) {
        Bukkit.getPluginManager().registerEvents(new ServerHostnameGetter(), plugin);
        Static.getAddressAsync(plugin, addr ->
                MCLibrary.setAddress(addr + ':' + Bukkit.getPort()));
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) throws UnknownHostException {
        String host = event.getHostname();
        String addrStr = host.substring(0, host.indexOf(':'));
        InetAddress addr = InetAddress.getByName(addrStr);
        if (!addr.isLoopbackAddress() && !addr.isSiteLocalAddress()) {
            MCLibrary.setAddress(host);
            event.getHandlers().unregister(this);
        }
    }
}
