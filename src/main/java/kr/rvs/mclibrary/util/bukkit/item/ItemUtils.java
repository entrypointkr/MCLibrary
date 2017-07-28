package kr.rvs.mclibrary.util.bukkit.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
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

    public static ItemRemoval getItemAmount(Player player, ItemStack item) {
        int am = 0;
        ItemRemoval removal = new ItemRemoval();
        Inventory inv = player.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack n = inv.getItem(i);
            if (n != null && n.getType() != Material.AIR && n.isSimilar(item)) {
                int a2 = n.getAmount();
                am += a2;
                removal.itemSlotAmount.add(a2);
                removal.sameItemSlot.add(i);
            }
        }
        removal.totalAmount = am;
        return removal;
    }

    public static int getLeftSize(Inventory inv, ItemStack item) {
        int max = item.getMaxStackSize();
        int left = 0;
        for (int i = inv.getSize(); i > 0; i--) {
            ItemStack n = inv.getItem(i);
            if (n != null && n.getType() != Material.AIR) {
                if (n.isSimilar(item)) {
                    left += max - n.getAmount();
                }
            } else {
                left += max;
            }
        }
        return left;
    }
}
