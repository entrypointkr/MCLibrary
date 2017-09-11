package kr.rvs.mclibrary.bukkit.inventory.gui.processor;

import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.bukkit.inventory.gui.Initializable;
import kr.rvs.mclibrary.bukkit.inventory.handler.SpecificSlotHandler;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.item.ItemUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-09-10.
 */
public class PagingInventoryProcessor implements InventoryProcessor, Initializable {
    public static final String PAGE = "%page%";
    private final ItemStack prevPageBtn;
    private final ItemStack pageInfoBtn;
    private final ItemStack nextPageBtn;
    private final int prevPageIndex;
    private final int pageInfoIndex;
    private final int nextPageIndex;
    private int currentPage = 1;
    private int size = -1;
    private int maxPage = -1;

    public PagingInventoryProcessor(ItemStack prevPageBtn, ItemStack pageInfoBtn, ItemStack nextPageBtn, int prevPageIndex, int pageInfoIndex, int nextPageIndex) {
        this.prevPageBtn = prevPageBtn;
        this.pageInfoBtn = pageInfoBtn;
        this.nextPageBtn = nextPageBtn;
        this.prevPageIndex = prevPageIndex;
        this.pageInfoIndex = pageInfoIndex;
        this.nextPageIndex = nextPageIndex;
    }

    public PagingInventoryProcessor() {
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
    public void initialize(GUI gui) {
        int size = gui.getSignature().getSize();
        Validate.isTrue(size > 18);
        int lastKey = gui.getSignature().getContents().lastKey();
        this.size = size - 9;
        this.maxPage = lastKey / size + lastKey % size > 0 ? 1 : 0;
        gui.getHandlers().addHandler(
                new PrevPageHandler(prevPageIndex),
                new NextPageHandler(nextPageIndex)
        );
    }

    @Override
    public void process(HumanEntity viewer, GUISignature signature, Inventory inv) {
        Validate.isTrue(size != -1 && maxPage != -1);

        int start = (currentPage - 1) * size;
        int end = currentPage * size;

        for (int i = start; i < end; i++) {
            inv.setItem(i - start, signature.getContents().get(i));
        }
        inv.setItem(prevPageIndex, new ItemBuilder(prevPageBtn)
                .addReplacements(PAGE, currentPage)
                .build());
        inv.setItem(pageInfoIndex, new ItemBuilder(pageInfoBtn)
                .addReplacements(PAGE, currentPage)
                .build());
        inv.setItem(nextPageIndex, new ItemBuilder(nextPageBtn)
                .addReplacements(PAGE, currentPage)
                .build());
    }

    class PrevPageHandler extends SpecificSlotHandler {
        public PrevPageHandler(Integer... slots) {
            super(slots);
        }

        @Override
        public void receive(GUIClickEvent e) {
            if (ItemUtils.isEmpty(e.getCurrentItem()) || currentPage < 2)
                return;

            currentPage--;
            e.getGui().open(e.getWhoClicked());
        }
    }

    class NextPageHandler extends SpecificSlotHandler {
        public NextPageHandler(Integer... slots) {
            super(slots);
        }

        @Override
        public void receive(GUIClickEvent e) {
            if (ItemUtils.isEmpty(e.getCurrentItem()) || currentPage >= maxPage)
                return;

            currentPage++;
            e.getGui().open(e.getWhoClicked());
        }
    }
}
