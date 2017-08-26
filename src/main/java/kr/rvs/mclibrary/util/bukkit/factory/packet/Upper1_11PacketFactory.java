package kr.rvs.mclibrary.util.bukkit.factory.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import kr.rvs.mclibrary.util.bukkit.factory.DefaultPacketFactory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
public class Upper1_11PacketFactory extends DefaultPacketFactory {
    @Override
    public PacketContainer createWindowItems(int windowId, List<ItemStack> items) {
        PacketContainer packet = MCUtils.getProtocolManager().createPacket(PacketType.Play.Server.WINDOW_ITEMS);
        packet.getIntegers().write(0, windowId);
        packet.getItemListModifier().write(0, items);

        return packet;
    }
}
