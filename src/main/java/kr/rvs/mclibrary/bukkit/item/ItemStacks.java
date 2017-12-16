package kr.rvs.mclibrary.bukkit.item;

import kr.rvs.mclibrary.collection.StringArrayList;
import kr.rvs.mclibrary.general.VarargsParser;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-11.
 */
public class ItemStacks {
    public static boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    public static void replaceString(ItemStack item, Object... args) {
        ItemMeta meta = item.getItemMeta();
        new VarargsParser(args).parse(section ->
                replaceString(meta, section.getString(0), section.getString(1)));
        item.setItemMeta(meta);
    }

    public static void replaceString(ItemMeta meta, String target, String replacement) {
        if (meta == null)
            return;

        String display = meta.getDisplayName();
        if (display != null) {
            display = display.replace(target, replacement);
        }

        List<String> lore = meta.getLore();
        StringArrayList newLore = new StringArrayList();
        if (lore != null) {
            for (String element : lore) {
                newLore.add(element.replace(target, replacement));
            }
        }

        meta.setDisplayName(display);
        meta.setLore(newLore);
    }

    private ItemStacks() {
    }
}
