package kr.rvs.mclibrary.util.bukkit.factory;

/**
 * Created by Junhyeong Lim on 2017-08-26.
 */
public class Platform {
    private static PacketFactory packetFactory;

    public static PacketFactory getPacketFactory() {
        if (packetFactory == null) {
            // TODO: version separate
            packetFactory = new DefaultPacketFactory();
        }

        return packetFactory;
    }
}
