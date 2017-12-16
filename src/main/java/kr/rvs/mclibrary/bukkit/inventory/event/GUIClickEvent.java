package kr.rvs.mclibrary.bukkit.inventory.event;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.item.ItemWrapper;
import kr.rvs.mclibrary.collection.OptionalHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-08-24.
 */
public class GUIClickEvent extends InventoryClickEvent {
    private static final OptionalHashMap<Integer, ItemStack> messageItemMap = new OptionalHashMap<>();
    private final InventoryClickEvent delegate;
    private final GUI gui;
    private int rawSlot;
    private int slot;

    public GUIClickEvent(InventoryClickEvent delegate, GUI gui) {
        super(delegate.getView(), delegate.getSlotType(), delegate.getRawSlot(), delegate.getClick(), delegate.getAction(), delegate.getHotbarButton());
        this.delegate = delegate;
        this.gui = gui;
        this.rawSlot = delegate.getRawSlot();
        this.slot = delegate.getSlot();
    }

    public void sendMessage(int tick, String title, String... messages) {
        if (!(getWhoClicked() instanceof Player))
            return;

        ItemWrapper clickedItem = new ItemWrapper(getInventory().getItem(getSlot()));
        if (clickedItem.isNotEmpty()) {
            if (!messageItemMap.containsKey(getSlot())) {
                messageItemMap.put(getSlot(), clickedItem.getHandle());

                // Delay & Restore
                Bukkit.getScheduler().runTaskLater(MCLibrary.getPlugin(), () ->
                        messageItemMap.getOptional(getSlot()).ifPresent(item -> {
                            getInventory().setItem(getSlot(), item);
                            messageItemMap.remove(getSlot());
                        }), tick);
            }
            ItemStack newItem = new ItemBuilder(clickedItem.getHandle())
                    .display(title)
                    .setLore(messages).build();

            getInventory().setItem(getSlot(), newItem);
        }
    }

    public void sendMessageWithDelay(int tick, int delay, String title, String... messages) {
        Bukkit.getScheduler().runTaskLater(MCLibrary.getPlugin(), () ->
                sendMessage(tick, title, messages), delay);
    }

    public void sendMessage(String title, String... messages) {
        sendMessage(60, title, messages);
    }

    public void sendMessageWithDelay(int delay, String title, String... messages) {
        Bukkit.getScheduler().runTaskLater(MCLibrary.getPlugin(), () ->
                sendMessage(title, messages), delay);
    }

    public void sendMessageWithDelay(String title, String... messages) {
        sendMessageWithDelay(0, title, messages);
    }

    public void setRawSlot(int rawSlot) {
        this.rawSlot = rawSlot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Optional<Player> getPlayer() {
        HumanEntity human = getWhoClicked();
        return human instanceof Player ?
                Optional.of((Player) human) :
                Optional.empty();
    }

    @Override
    public Inventory getClickedInventory() {
        return delegate.getClickedInventory();
    }

    @Override
    public InventoryType.SlotType getSlotType() {
        return delegate.getSlotType();
    }

    @Override
    public ItemStack getCursor() {
        return delegate.getCursor();
    }

    @Override
    public ItemStack getCurrentItem() {
        return delegate.getCurrentItem();
    }

    @Override
    public boolean isRightClick() {
        return delegate.isRightClick();
    }

    @Override
    public boolean isLeftClick() {
        return delegate.isLeftClick();
    }

    @Override
    public boolean isShiftClick() {
        return delegate.isShiftClick();
    }

    @Override
    @Deprecated
    public void setCursor(ItemStack stack) {
        delegate.setCursor(stack);
    }

    @Override
    public void setCurrentItem(ItemStack stack) {
        delegate.setCurrentItem(stack);
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public int getRawSlot() {
        return rawSlot;
    }

    @Override
    public int getHotbarButton() {
        return delegate.getHotbarButton();
    }

    @Override
    public InventoryAction getAction() {
        return delegate.getAction();
    }

    @Override
    public ClickType getClick() {
        return delegate.getClick();
    }

    @Override
    public HandlerList getHandlers() {
        return delegate.getHandlers();
    }

    public static HandlerList getHandlerList() {
        return InventoryClickEvent.getHandlerList();
    }

    @Override
    public HumanEntity getWhoClicked() {
        return delegate.getWhoClicked();
    }

    @Override
    public void setResult(Result newResult) {
        delegate.setResult(newResult);
    }

    @Override
    public Result getResult() {
        return delegate.getResult();
    }

    @Override
    public boolean isCancelled() {
        return delegate.isCancelled();
    }

    @Override
    public void setCancelled(boolean toCancel) {
        delegate.setCancelled(toCancel);
    }

    @Override
    public Inventory getInventory() {
        return delegate.getInventory();
    }

    @Override
    public List<HumanEntity> getViewers() {
        return delegate.getViewers();
    }

    @Override
    public InventoryView getView() {
        return delegate.getView();
    }

    @Override
    public String getEventName() {
        return delegate.getEventName();
    }

    public GUI getGui() {
        return gui;
    }
}
