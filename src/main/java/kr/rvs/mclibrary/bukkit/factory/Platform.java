package kr.rvs.mclibrary.bukkit.factory;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.factory.packet.DefaultPacketFactory;
import kr.rvs.mclibrary.bukkit.factory.packet.ModernPacketFactory;
import kr.rvs.mclibrary.bukkit.factory.packet.PacketFactory;
import kr.rvs.mclibrary.general.Version;

/**
 * Created by Junhyeong Lim on 2017-08-26.
 */
public class Platform {
    private static PacketFactory packetFactory;

    public static PacketFactory getPacketFactory() {
        if (packetFactory == null) {
            if (MCLibrary.getBukkitVersion().afterEquals(new Version(1, 11, 0)))
                packetFactory = new ModernPacketFactory();
            else
                packetFactory = new DefaultPacketFactory();
        }

        return packetFactory;
    }
}
