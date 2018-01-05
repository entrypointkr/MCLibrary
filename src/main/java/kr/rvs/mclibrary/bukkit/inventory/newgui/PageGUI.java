package kr.rvs.mclibrary.bukkit.inventory.newgui;

import kr.rvs.mclibrary.bukkit.inventory.Inventories;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-12-11.
 */
public class PageGUI extends GUI {
    private int currPage = 0;
    private final int spaceSize;
    private final int maxPage;
    private final int prevPageBtnIndex;
    private final int nextPageBtnIndex;

    public PageGUI(GUIData data) {
        super(data);
        this.spaceSize = data.size() - 18;
        this.maxPage = lastKey() / spaceSize;
        this.prevPageBtnIndex = data.size() - 7;
        this.nextPageBtnIndex = data.size() - 3;
    }

    private int lastKey() {
        int lastKey = 0;
        ItemStack[] contents = getData().contents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null)
                lastKey = i;
        }
        return lastKey;
    }

    private void fail(Player player, int slot) {
        Inventories.sendMessage(player, "§c마지막 페이지입니다.", slot);
    }

    private void prevPage(Player clicker) {
        if (currPage > 0) {
            currPage--;
        } else {
            fail(clicker, prevPageBtnIndex);
        }
    }

    private void nextPage(Player clicker) {
        if (currPage < maxPage) {
            currPage++;
        } else {
            fail(clicker, nextPageBtnIndex);
        }
    }

    private boolean isFrame(int slot) {
        int size = getData().size();
        return (slot >= 0 && slot <= 8) || (slot >= size - 9 && slot <= size);
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {
        getHandlers().notify(event);
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        getHandlers().notify(event);
    }

    private InventoryClickEvent convert(InventoryClickEvent event) {
        int rawSlot = event.getRawSlot();
        int convertedSlot = rawSlot - 9 + (currPage * spaceSize);
        return new InventoryClickEvent(
                event.getView(), event.getSlotType(), convertedSlot,
                event.getClick(), event.getAction(), event.getHotbarButton()
        );
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int rawSlot = event.getRawSlot();
        boolean isFrame = isFrame(rawSlot);
        if (isFrame) {
            event.setCancelled(true);
        }
        if (rawSlot == prevPageBtnIndex) {
            prevPage(player);
        } else if (rawSlot == nextPageBtnIndex) {
            nextPage(player);
        } else if (!isFrame) {
            InventoryClickEvent converted = convert(event);
            getHandlers().notify(converted);
            event.setCancelled(converted.isCancelled());
            return;
        }
        open(event.getWhoClicked());
    }

    @Override
    protected void onDrag(InventoryDragEvent event) {
        getHandlers().notify(event);
    }

    private void setLayout(Inventory inv) {
        String title = getData().title();
        int size = getData().size();
        int lastLineFirstIndex = size - 9;
        ItemStack wall = new ItemBuilder(Material.STAINED_GLASS)
                .display("")
                .build();
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, wall);
        }
        for (int i = lastLineFirstIndex; i < size; i++) {
            inv.setItem(i, wall);
        }

        inv.setItem(4, new ItemBuilder(Material.NETHER_STAR).display(title).build());
        inv.setItem(prevPageBtnIndex, new ItemBuilder(Material.WOOD_BUTTON).build());
        inv.setItem(nextPageBtnIndex, new ItemBuilder(Material.WOOD_BUTTON).build());
    }

    private void setContents(Inventory inv) {
        GUIData data = getData();
        ItemStack[] contents = data.contents();
        int start = currPage * spaceSize;
        int end = start + spaceSize;
        for (int i = start; i < end; i++) {
            if (contents.length <= i)
                break;
            ItemStack item = contents[i];
            if (item != null && item.getType() != Material.AIR) {
                inv.setItem(i - start + 9, item);
            }
        }
    }

    @Override
    protected Inventory createInventory() {
        GUIData data = getData();
        Inventory inv = Bukkit.createInventory(null, data.size(), data.title());
        setLayout(inv);
        setContents(inv);
        return inv;
    }
}
