package kr.rvs.mclibrary.util.bukkit.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public static ItemRemoval getItemAmount(Player player, ItemStack item) {
        int hasAmount = 0;
        ItemRemoval removal = new ItemRemoval();
        Inventory inv = player.getInventory();

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack elemItem = inv.getItem(i);
            if (elemItem != null && elemItem.getType() != Material.AIR && elemItem.isSimilar(item)) {
                int itemAmount = elemItem.getAmount();
                hasAmount += itemAmount;
                removal.itemSlotAmount.add(itemAmount);
                removal.sameItemSlot.add(i);
            }
        }

        removal.totalAmount = hasAmount;
        return removal;
    }

    public static int getLeftSize(Inventory inv, ItemStack item) {
        int max = item.getMaxStackSize();
        int left = 0;
        for (int i = inv.getSize(); i > 0; i--) {
            ItemStack elemeItem = inv.getItem(i);
            if (elemeItem != null && elemeItem.getType() != Material.AIR) {
                if (elemeItem.isSimilar(item)) {
                    left += max - elemeItem.getAmount();
                }
            } else {
                left += max;
            }
        }
        return left;
    }

    static class ItemRemoval {
        List<Integer> sameItemSlot = new ArrayList<>();
        List<Integer> itemSlotAmount = new ArrayList<>();
        int totalAmount;

        public void removeItem(Player player, int am) {
            Inventory inv = player.getInventory();
            for (int i = 0; i < sameItemSlot.size(); i++) {
                int amount = itemSlotAmount.get(i);
                int slot = sameItemSlot.get(i);
                if (amount < am) {
                    am -= amount;
                    inv.setItem(slot, new ItemStack(Material.AIR));
                } else if (amount > am) {
                    amount -= am;
                    inv.getItem(slot).setAmount(amount);
                    return;
                } else {
                    inv.setItem(slot, new ItemStack(Material.AIR));
                    return;
                }
            }
        }
    }
}
