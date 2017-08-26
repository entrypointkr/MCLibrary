package kr.rvs.mclibrary.util.bukkit.factory;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-08-26.
 */
public class DefaultPacketFactory implements PacketFactory {
    @Override
    public PacketContainer createSetSlot(int windowId, int itemSlot, ItemStack item) {
        PacketContainer packet = MCUtils.getProtocolManager().createPacket(PacketType.Play.Server.SET_SLOT);
        packet.getIntegers()
                .write(0, windowId)
                .write(1, itemSlot);
        packet.getItemModifier().write(0, item);

        return packet;
    }

    @Override
    public PacketContainer createWindowItems(int windowId, List<ItemStack> items) {
        PacketContainer packet = MCUtils.getProtocolManager().createPacket(PacketType.Play.Server.WINDOW_ITEMS);
        packet.getIntegers().write(0, windowId);
        packet.getItemArrayModifier().write(0, (ItemStack[]) items.toArray());

        return packet;
    }
}
