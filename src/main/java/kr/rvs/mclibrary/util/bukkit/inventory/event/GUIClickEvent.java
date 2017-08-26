package kr.rvs.mclibrary.util.bukkit.inventory.event;

import com.comphenix.protocol.events.PacketContainer;
import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.util.bukkit.MCUtils;
import kr.rvs.mclibrary.util.bukkit.factory.Platform;
import kr.rvs.mclibrary.util.bukkit.item.ItemBuilder;
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
public class GUIClickEvent extends InventoryClickEvent implements MessageSendable {
    private final InventoryClickEvent delegate;

    public GUIClickEvent(InventoryClickEvent delegate) {
        super(delegate.getView(), delegate.getSlotType(), delegate.getRawSlot(), delegate.getClick(), delegate.getAction(), delegate.getHotbarButton());
        this.delegate = delegate;
    }

    @Override
    public void sendMessage(String title, String... messages) {
        if (!(getWhoClicked() instanceof Player))
            return;

        Optional.ofNullable(getInventory().getItem(getRawSlot())).ifPresent(item -> {
            ItemStack newItem = new ItemBuilder(item)
                    .display(title)
                    .lore(messages).build();
            PacketContainer packet = Platform.getPacketFactory().createSetSlot(0, getRawSlot() - getInventory().getSize(), newItem);
            Bukkit.getScheduler().runTaskLater(MCLibrary.getPlugin(), () ->
                    MCUtils.sendPacket((Player) getWhoClicked(), packet),
                    2);
        });
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
        return delegate.getSlot();
    }

    @Override
    public int getRawSlot() {
        return delegate.getRawSlot();
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
}
