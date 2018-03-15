package kr.rvs.mclibrary.bukkit.inventory;

import com.comphenix.protocol.events.PacketContainer;
import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.item.ItemStacks;
import kr.rvs.mclibrary.bukkit.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-08-20.
 */
public class Inventories {
    public static Map<Integer, ItemStack> toMap(Inventory inventory) {
        Map<Integer, ItemStack> ret = new HashMap<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            ret.put(i, inventory.getItem(i));
        }
        return ret;
    }

    public static void putAll(Inventory inv, List<ItemStack> contents) {
        for (int i = 0; i < contents.size(); i++) {
            inv.setItem(i, contents.get(i));
        }
    }

    public static void putAll(Inventory inv, Map<Integer, ItemStack> contents) {
        for (Map.Entry<Integer, ItemStack> entry : contents.entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue());
        }
    }

    public static int hasItems(Inventory inv, ItemStack item) {
        int hasAmount = 0;
        for (ItemStack elemItem : inv) {
            if (elemItem == null || elemItem.getType() == Material.AIR
                    || !elemItem.isSimilar(item))
                continue;
            hasAmount += elemItem.getAmount();
        }
        return hasAmount;
    }

    public static int hasItems(InventoryHolder holder, ItemStack item) {
        return hasItems(holder.getInventory(), item);
    }

    public static boolean hasItem(Inventory inv, ItemStack item, int amount) {
        int hasAmount = 0;
        for (ItemStack elemItem : inv) {
            if (elemItem == null || elemItem.getType() == Material.AIR
                    || !elemItem.isSimilar(item))
                continue;
            hasAmount += elemItem.getAmount();
            if (hasAmount >= amount)
                return true;
        }
        return false;
    }

    public static boolean hasItem(InventoryHolder holder, ItemStack item, int amount) {
        return hasItem(holder.getInventory(), item, amount);
    }

    public static boolean takeItem(Inventory inv, ItemStack item, int takeAmount) {
        Set<Integer> removeSlots = new HashSet<>(takeAmount);
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack elemItem = inv.getItem(i);
            if (elemItem == null || elemItem.getType() == Material.AIR
                    || !elemItem.isSimilar(item))
                continue;

            int amount = elemItem.getAmount();
            if (takeAmount > amount) {
                takeAmount -= amount;
                removeSlots.add(i);
            } else {
                if (takeAmount < amount) {
                    elemItem.setAmount(amount - takeAmount);
                } else {
                    removeSlots.add(i);
                }
                removeSlots.forEach(inv::clear);
                return true;
            }
        }
        return false;
    }

    public static boolean takeItem(InventoryHolder holder, ItemStack item, int takeAmount) {
        return takeItem(holder.getInventory(), item, takeAmount);
    }

    /**
     * @param inv Inventory
     * @param item ItemStack
     * @param takeAmount Take amount
     * @return Returns insufficient item amount. If all taken, will be return 0.
     */
    public static int takeItems(Inventory inv, ItemStack item, int takeAmount) {
        Map<Integer, ItemStack> removeItemMap = new HashMap<>(takeAmount);
        int take = takeAmount;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack elemItem = inv.getItem(i);
            if (elemItem == null || elemItem.getType() == Material.AIR
                    || !elemItem.isSimilar(item))
                continue;

            int amount = elemItem.getAmount();
            if (take > amount) {
                take -= amount;
                removeItemMap.put(i, elemItem);
            } else {
                if (take < amount) {
                    elemItem.setAmount(amount - take);
                } else {
                    removeItemMap.put(i, elemItem);
                }
                removeItemMap.keySet().forEach(inv::clear);
                return 0;
            }
        }

        int result = takeAmount;
        for (ItemStack elemItem : removeItemMap.values()) {
            result -= elemItem.getAmount();
        }
        return result;
    }

    public static int takeItems(Inventory inv, ItemStack item) {
        return item != null
                ? takeItems(inv, item, item.getAmount())
                : 0;
    }

    public static int takeItems(InventoryHolder holder, ItemStack item, int takeAmount) {
        return takeItems(holder.getInventory(), item, takeAmount);
    }

    public static int takeItems(InventoryHolder holder, ItemStack item) {
        return takeItems(holder.getInventory(), item);
    }

    public static int hasSpace(Inventory inv, ItemStack item) {
        int space = 0;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack element = inv.getItem(i);
            if (element == null || element.getType() == Material.AIR) {
                space += item.getMaxStackSize();
            } else if (element.isSimilar(item)) {
                space += item.getMaxStackSize() - element.getAmount();
            }
        }
        return space;
    }

    public static int hasSpace(InventoryHolder holder, ItemStack item) {
        return hasSpace(holder.getInventory(), item);
    }

    public static boolean hasSpace(Inventory inv, ItemStack item, int amount) {
        return hasSpace(inv, item) >= amount;
    }

    public static boolean hasSpace(InventoryHolder holder, ItemStack item, int amount) {
        return hasSpace(holder.getInventory(), item, amount);
    }

    public static Map<Integer, ItemStack> giveItem(Inventory inv, ItemStack item, int amount) {
        Map<Integer, ItemStack> map = new HashMap<>();
        if (ItemStacks.isNotEmpty(item)) {
            int maxStack = item.getMaxStackSize();
            for (int i = 0; i < amount / maxStack; i++) {
                map.putAll(inv.addItem(new ItemBuilder(item).amount(maxStack).build()));
            }
            int remain = amount % maxStack;
            if (remain > 0) {
                map.putAll(inv.addItem(new ItemBuilder(item).amount(remain).build()));
            }
        }
        return map;
    }

    public static Map<Integer, ItemStack> giveItem(Inventory inv, ItemStack item) {
        return item != null
                ? giveItem(inv, item, item.getAmount())
                : new HashMap<>();
    }

    public static Map<Integer, ItemStack> giveItem(InventoryHolder holder, ItemStack item, int amount) {
        return giveItem(holder.getInventory(), item, amount);
    }

    public static Map<Integer, ItemStack> giveItem(InventoryHolder holder, ItemStack item) {
        return giveItem(holder.getInventory(), item);
    }

    public static boolean isEmpty(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType() != Material.AIR)
                return false;
        }
        return true;
    }

    public static boolean isEmpty(InventoryHolder holder) {
        return isEmpty(holder.getInventory());
    }

    public static Map<Integer, ItemStack> transfer(Inventory inv, Inventory target) {
        Map<Integer, ItemStack> failMap = new HashMap<>();
        for (ItemStack item : inv) {
            if (item != null) {
                failMap.putAll(target.addItem(item));
            }
        }
        return failMap;
    }

    public static void sendMessage(Player player, String message, int slot, int second) {
        ItemStack item = player.getOpenInventory().getItem(slot);
        if (item != null && item.getType() != Material.AIR) {
            ItemStack newItem = new ItemBuilder(item)
                    .display(message)
                    .build();
            PlayerWrapper wrapper = new PlayerWrapper(player);

            Bukkit.getScheduler().runTask(MCLibrary.getPlugin(), () -> {
                PacketContainer packet = MCLibrary.getPacketFactory().createSetSlot(
                        wrapper.getContainerCounter(), slot, newItem
                );
                MCUtils.sendPacket(player, packet);
                Bukkit.getScheduler().runTaskLater(MCLibrary.getPlugin(), () -> {
                    packet.getItemModifier().write(0, item);
                    MCUtils.sendPacket(player, packet);
                }, second * 20L);
            });
        }
    }

    public static void sendMessage(Player player, String message, int slot) {
        sendMessage(player, message, slot, 3);
    }

    public static void sendMessage(InventoryClickEvent event, String message, int second) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            sendMessage(player, message, event.getRawSlot(), second);
        }
    }

    public static void sendMessage(InventoryClickEvent event, String message) {
        sendMessage(event, message, 3);
    }

    private Inventories() {
    }
}
