package kr.rvs.mclibrary.util.bukkit.factory.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.Lists;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
public class ModernPacketFactory extends DefaultPacketFactory {
    @Override
    public PacketContainer createWindowItems(int windowId, ItemStack[] items) {
        PacketContainer packet = MCUtils.getProtocolManager().createPacket(PacketType.Play.Server.WINDOW_ITEMS);
        packet.getIntegers().write(0, windowId);
        packet.getItemListModifier().write(0, Lists.newArrayList(items));

        return packet;
    }
}
