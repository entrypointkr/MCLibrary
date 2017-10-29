package kr.rvs.mclibrary.bukkit.inventory.gui.factory;

import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIClickHandler;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIHandler;
import kr.rvs.mclibrary.bukkit.inventory.gui.InventoryFactory;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.DelegateClickHandler;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.EventCancelHandler;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.item.ItemUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Junhyeong Lim on 2017-09-12.
 */
public class PagingInventoryProcessor extends InventoryProcessor {
    public static final String PAGE = "%page%";
    public static final String MAX_PAGE = "%max-page%";
    private final ItemStack prevPageBtn;
    private final ItemStack pageInfoBtn;
    private final ItemStack nextPageBtn;
    private final int prevPageIndex;
    private final int pageInfoIndex;
    private final int nextPageIndex;
    private int currentPage = 1;
    private int size = -1;
    private int maxPage = -1;

    public PagingInventoryProcessor(InventoryFactory factory, ItemStack prevPageBtn, ItemStack pageInfoBtn, ItemStack nextPageBtn, int prevPageIndex, int pageInfoIndex, int nextPageIndex) {
        super(factory);
        this.prevPageBtn = prevPageBtn;
        this.pageInfoBtn = pageInfoBtn;
        this.nextPageBtn = nextPageBtn;
        this.prevPageIndex = prevPageIndex;
        this.pageInfoIndex = pageInfoIndex;
        this.nextPageIndex = nextPageIndex;
    }

    public PagingInventoryProcessor(InventoryFactory factory) {
        this(
                factory,
                new ItemBuilder(Material.DIODE)
                        .display("&7이전 페이지")
                        .build(),
                new ItemBuilder(Material.PAPER)
                        .display(String.format("&e페이지: &a%s/%s", PAGE, MAX_PAGE))
                        .build(),
                new ItemBuilder(Material.REDSTONE_COMPARATOR)
                        .display("&a다음 페이지")
                        .build(),
                3, 4, 5
        );
    }

    public PagingInventoryProcessor() {
        this(new BaseInventoryFactory());
    }

    @Override
    public void initialize(GUI gui) {
        int size = gui.getSignature().getSize();
        Validate.isTrue(size >= 18);
        int lastKey = gui.getSignature().getContents().lastKey();
        this.size = size - 9;
        this.maxPage = lastKey / this.size + (lastKey + 1 % this.size > 0 ? 1 : 0);
        gui.getHandlers().addFirst(
                new EventCancelHandler(),
                new DelegateClickHandler(new PrevPageHandler(), getPrevPageIndex()),
                new DelegateClickHandler(new NextPageHandler(), getNextPageIndex()),
                new EventSlotModerator()
        );
    }

    private int getPrevPageIndex() {
        return prevPageIndex + size;
    }

    private int getPageInfoIndex() {
        return pageInfoIndex + size;
    }

    private int getNextPageIndex() {
        return nextPageIndex + size;
    }

    @Override
    public void process(GUI gui, HumanEntity viewer, Inventory inv) {
        Validate.isTrue(size != -1 && maxPage != -1);

        int start = (currentPage - 1) * size;
        int end = currentPage * size;

        for (int i = start; i < end; i++) {
            inv.setItem(i - start, gui.getSignature().getContents().get(i));
        }
        inv.setItem(getPrevPageIndex(), new ItemBuilder(prevPageBtn)
                .replacement(
                        PAGE, currentPage,
                        MAX_PAGE, maxPage
                )
                .build());
        inv.setItem(getPageInfoIndex(), new ItemBuilder(pageInfoBtn)
                .replacement(
                        PAGE, currentPage,
                        MAX_PAGE, maxPage
                )
                .amount(currentPage)
                .build());
        inv.setItem(getNextPageIndex(), new ItemBuilder(nextPageBtn)
                .replacement(
                        PAGE, currentPage,
                        MAX_PAGE, maxPage
                )
                .build());
    }

    class EventSlotModerator implements GUIHandler {
        @Override
        public void handle(GUIEvent<InventoryEvent> event) {
            event.getEvent(GUIClickEvent.class).ifPresent(e -> {
                int slot = e.getRawSlot();
                if (slot >= 0 && slot < size) {
                    e.setRawSlot(e.getRawSlot() + (currentPage - 1) * size);
                }
                if (slot >= size)
                    e.setIgnore(true);
            });
        }
    }

    class PrevPageHandler implements GUIClickHandler {
        @Override
        public void click(GUIEvent<GUIClickEvent> event) {
            GUIClickEvent e = event.getEvent();
            if (!ItemUtils.isEmpty(e.getCurrentItem()) && currentPage >= 2) {
                currentPage--;
                e.getGui().open(e.getWhoClicked());
            } else {
                e.sendMessage("&c페이지의 끝입니다.");
            }
        }
    }

    class NextPageHandler implements GUIClickHandler {
        @Override
        public void click(GUIEvent<GUIClickEvent> event) {
            GUIClickEvent e = event.getEvent();
            if (!ItemUtils.isEmpty(e.getCurrentItem()) && currentPage < maxPage) {
                currentPage++;
                e.getGui().open(e.getWhoClicked());
            } else {
                e.sendMessage("&c페이지의 끝입니다.");
            }
        }
    }
}
