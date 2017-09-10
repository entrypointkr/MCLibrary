package kr.rvs.mclibrary.bukkit.factory.packet;

import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-08-26.
 */
public interface PacketFactory {
    PacketContainer createSetSlot(int windowId, int itemSlot, ItemStack item);

    PacketContainer createWindowItems(int windowId, ItemStack[] items);
}
