package kr.rvs.mclibrary.util.bukkit.factory;

import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-08-26.
 */
public interface PacketFactory {
    PacketContainer createSetSlot(int windowId, int itemSlot, ItemStack item);

    PacketContainer createWindowItems(int windowId, List<ItemStack> items);
}
