package kr.rvs.mclibrary.util.bukkit.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemUtils {

    private static HashMap<Integer,Integer> maxStack = new HashMap<>();
    static{
        maxStack.put(282,1);
        maxStack.put(354,1);
        maxStack.put(256,1);
        maxStack.put(257,1);
        maxStack.put(259,1);
        maxStack.put(269,1);
        maxStack.put(270,1);
        maxStack.put(271,1);
        maxStack.put(273,1);
        maxStack.put(274,1);
        maxStack.put(275,1);
        maxStack.put(277,1);
        maxStack.put(278,1);
        maxStack.put(279,1);
        maxStack.put(284,1);
        maxStack.put(285,1);
        maxStack.put(286,1);
        maxStack.put(290,1);
        maxStack.put(291,1);
        maxStack.put(292,1);
        maxStack.put(293,1);
        maxStack.put(294,1);
        maxStack.put(346,1);
        maxStack.put(359,1);
        maxStack.put(403,1);
        maxStack.put(261,1);

        maxStack.put(261,1);
        maxStack.put(267,1);
        maxStack.put(268,1);
        maxStack.put(272,1);
        maxStack.put(276,1);
        maxStack.put(283,1);
        maxStack.put(298,1);
        maxStack.put(299,1);
        maxStack.put(300,1);
        maxStack.put(301,1);
        maxStack.put(302,1);
        maxStack.put(303,1);
        maxStack.put(304,1);
        maxStack.put(305,1);
        maxStack.put(306,1);
        maxStack.put(307,1);
        maxStack.put(308,1);
        maxStack.put(309,1);
        maxStack.put(310,1);
        maxStack.put(311,1);
        maxStack.put(312,1);
        maxStack.put(313,1);

        maxStack.put(313,1);
        maxStack.put(314,1);
        maxStack.put(315,1);
        maxStack.put(316,1);
        maxStack.put(317,1);
        maxStack.put(373,1);
        maxStack.put(325,16);
        maxStack.put(368,16);
        maxStack.put(332,16);
        maxStack.put(326,1);
        maxStack.put(327,1);
        maxStack.put(335,1);


        maxStack.put(2256,1);
        maxStack.put(2257,1);
        maxStack.put(2258,1);
        maxStack.put(2259,1);
        maxStack.put(2260,1);
        maxStack.put(2261,1);
        maxStack.put(2262,1);
        maxStack.put(2263,1);
        maxStack.put(2264,1);
        maxStack.put(2265,1);
        maxStack.put(2266,1);
        maxStack.put(2267,1);
        maxStack.put(328,1);
        maxStack.put(329,1);
        maxStack.put(333,1);
        maxStack.put(342,1);
        maxStack.put(343,1);
        maxStack.put(398,1);
        maxStack.put(407,1);
        maxStack.put(408,1);
        maxStack.put(324,1);
        maxStack.put(330,1);
        maxStack.put(355,1);
        maxStack.put(323,16);
    }

    static class ItemRemoval {
        List<Integer> sameItemSlot = new ArrayList<>();
        List<Integer> itemSlotAmount = new ArrayList<>();
        int totalAmount;
        public void removeItem(Player player, int am){
            Inventory inv = player.getInventory();
            for(int i = 0;i < sameItemSlot.size();i++){
                int amount = itemSlotAmount.get(i);
                int slot = sameItemSlot.get(i);
                if(amount < am)
                {
                    am -= amount;
                    inv.setItem(slot,new ItemStack(Material.AIR));
                }else if(amount > am){
                    amount -= am;
                    inv.getItem(slot).setAmount(amount);
                    return;
                }else{
                    inv.setItem(slot,new ItemStack(Material.AIR));
                    return;
                }
            }
        }
    }
    public static ItemRemoval getItemAmount(Player player, ItemStack item){
        int am = 0;
        ItemRemoval removal = new ItemRemoval();
        Inventory inv = player.getInventory();
        for(int i = 0;i < inv.getSize();i++){
            ItemStack n = inv.getItem(i);
            if(n != null && n.getType() != Material.AIR && n.isSimilar(item))
            {
                int a2 = n.getAmount();
                am += a2;
                removal.itemSlotAmount.add(a2);
                removal.sameItemSlot.add(i);
            }
        }
        removal.totalAmount = am;
        return removal;
    }

    public static int getLeftSize(Inventory inv,ItemStack item){
         int max = item.getMaxStackSize();
        int left = 0;
           for(int i = inv.getSize();i > 0;i--){
                ItemStack n = inv.getItem(i);
                if(n != null && n.getType() != Material.AIR) {
                   if(n.isSimilar(item)) {
                         left += max - n.getAmount();
                    }
                } else {
                    left += max;
                }
            }
            return left;
        }
}
