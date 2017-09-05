package kr.rvs.mclibrary.util.bukkit.inventory.factory;

import kr.rvs.mclibrary.util.bukkit.inventory.GUI;
import kr.rvs.mclibrary.util.bukkit.inventory.InventoryUtils;
import kr.rvs.mclibrary.util.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.util.bukkit.inventory.handler.SpecificSlotHandler;
import kr.rvs.mclibrary.util.bukkit.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-09-05.
 */
public class PagingInventoryFactory extends InventoryFactory {
    public static final String PAGE = "%page%";

    private final ItemStack prevPageItem;
    private final ItemStack pageInfoItem;
    private final ItemStack nextPageItem;
    private final int prevPageSlot;
    private final int pageInfoSlot;
    private final int nextPageSlot;
    private int currentPage = 1;

    public PagingInventoryFactory(ItemStack prevPageItem, ItemStack pageInfoItem, ItemStack nextPageItem, int prevPageSlot, int pageInfoSlot, int nextPageSlot) {
        this.prevPageItem = prevPageItem;
        this.pageInfoItem = pageInfoItem;
        this.nextPageItem = nextPageItem;
        this.prevPageSlot = prevPageSlot;
        this.pageInfoSlot = pageInfoSlot;
        this.nextPageSlot = nextPageSlot;
    }

    public PagingInventoryFactory() {
        this(
                new ItemBuilder(Material.DIODE)
                        .display("&7이전 페이지")
                        .build(),
                new ItemBuilder(Material.PAPER)
                        .display("&6현재 페이지: " + PAGE)
                        .build(),
                new ItemBuilder(Material.REDSTONE_COMPARATOR)
                        .display("&a다음 페이지")
                        .build(),
                49, 50, 51
        );
    }

    @Override
    public void initialize(GUI gui, InventoryType type, String title, int size, Map<Integer, ItemStack> contents) {
        super.initialize(gui, type, title, size, contents);
        gui.addHandler(new PagingHandler(49, 51));
    }

    @Override
    public Inventory create(HumanEntity viewer) {
        Inventory inv = createDefaultInventory();
        Map<Integer, ItemStack> contents = getContents();
        int size = contents.size();
        int start = (currentPage - 1) * getSize();
        int end = Math.min(currentPage * getSize(), size);

        for (int i = start; i < end; i++) {
            inv.setItem(i - start, contents.get(i));
        }

        if (currentPage > 1)
            inv.setItem(prevPageSlot, prevPageItem);
        if (size > end)
            inv.setItem(nextPageSlot, nextPageItem);
        inv.setItem(pageInfoSlot, pageInfoItem);

        return inv;
    }

    class PagingHandler extends SpecificSlotHandler {
        private final int prevPageSlot;
        private final int nextPageSlot;

        public PagingHandler(int prevPageSlot, int nextPageSlot) {
            super(prevPageSlot, nextPageSlot);
            this.prevPageSlot = prevPageSlot;
            this.nextPageSlot = nextPageSlot;
        }

        @Override
        public void receive(GUIClickEvent e) {
            int slot = InventoryUtils.movedSlot(e);
            ItemStack item = e.getCurrentItem();

            if (item != null) {
                if (slot == prevPageSlot)
                    currentPage--;
                else if (slot == nextPageSlot)
                    currentPage++;
            }

            e.getGui().open(e.getWhoClicked());
        }
    }
}
