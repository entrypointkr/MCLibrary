package kr.rvs.mclibrary.bukkit.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.Static;
import kr.rvs.mclibrary.bukkit.MCUtils;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

/**
 * Created by Junhyeong Lim on 2017-08-26.
 */
public class PacketMonitoringListener extends PacketAdapter {
    public static void register(Plugin plugin) {
        MCUtils.getProtocolManager().removePacketListeners(plugin);
        MCUtils.getProtocolManager().addPacketListener(new PacketMonitoringListener());
    }

    public PacketMonitoringListener() {
        super(MCLibrary.getPlugin(), PacketType.values());
    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {
        Static.log(Level.INFO, "S -> C " + getPacketName(packetEvent));
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {
        Static.log(Level.INFO, "S <- C " + getPacketName(packetEvent));
    }

    private String getPacketName(PacketEvent event) {
        return event.getPacket().getHandle().getClass().getSimpleName();
    }
}
