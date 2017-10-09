package kr.rvs.mclibrary.plugin;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.collection.EntityHashSet;
import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandType;
import kr.rvs.mclibrary.bukkit.command.annotation.Command;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUIEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignature;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.ClickHandler;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.EventCancelHandler;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
@Command(
        args = "mclibrary"
)
public class LibraryCommand {
    private static final EntityHashSet<Player> INFO_LISTENERS = new EntityHashSet<>();
    private final MCLibrary instance = (MCLibrary) MCLibrary.getPlugin();

    public static void init(MCLibrary plugin) {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            @SuppressWarnings("deprecation")
            public void onInteract(PlayerInteractEvent e) {
                if (e.getHand() != EquipmentSlot.HAND)
                    return;

                Player player = e.getPlayer();
                Block block = e.getClickedBlock();
                if (block == null || !INFO_LISTENERS.contains(player))
                    return;

                Location loc = block.getLocation();
                CommandSenderWrapper wrapper = new CommandSenderWrapper(player);
                wrapper.sendMessage("---------------------------------------------");
                wrapper.sendMessage("&eworld: &f" + loc.getWorld().getName());
                wrapper.sendMessage(String.format("&ex: &f%s (%s)", loc.getBlockX(), loc.getX()));
                wrapper.sendMessage(String.format("&ey: &f%s (%s)", loc.getBlockY(), loc.getY()));
                wrapper.sendMessage(String.format("&ez: &f%s (%s)", loc.getBlockZ(), loc.getZ()));
                wrapper.sendMessage(String.format("&eblock: &f%s:%s (%s)", block.getTypeId(), block.getData(), block.getType().name()));
            }
        }, plugin);
    }

    @Command(
            args = "reload",
            perm = "mclibrary.reload",
            desc = "설정파일을 리로드합니다."
    )
    public void reloadCommand(CommandSenderWrapper wrapper, CommandArguments args) {
        FileConfiguration config = instance.getConfig();
        instance.reloadConfig();
        instance.configInit();
        for (String key : config.getKeys(true)) {
            wrapper.sendMessage(key + ": " + config.get(key));
        }
    }

    @Command(
            args = "killall",
            perm = "mclibrary.killall",
            usage = "[월드]",
            desc = "모든 몬스터를 제거합니다."
    )
    public void killallCommand(CommandSenderWrapper wrapper, CommandArguments args) {
        World world = args.size() > 0 ? args.getWorld(0) : wrapper.isPlayer() ? wrapper.getPlayer().getWorld() : null;
        if (world != null) {
            world.getEntities().stream()
                    .filter(entity -> entity instanceof Creature)
                    .forEach(Entity::remove);
        } else {
            throw new InvalidUsageException(this, "올바른 월드명을 입력하세요.");
        }
    }

    @Command(
            type = CommandType.PLAYER,
            args = "gui",
            perm = "mclibrary.gui",
            desc = "테스트 gui 를 엽니다."
    )
    public void guiCommand(CommandSenderWrapper wrapper, CommandArguments args) {
        new GUI(
                new GUISignature(InventoryType.CHEST)
                        .title("MCLibrary GUI")
                        .item(13, new ItemBuilder(Material.MAP).display("MCLibrary version").build()),
                new EventCancelHandler(),
                new ClickHandler(13) {
                    @Override
                    public void click(GUIEvent<GUIClickEvent> event) {
                        event.getEvent().sendMessage(
                                "&aHello,",
                                "&e" + instance.getDescription().getFullName()
                        );
                    }
                }
        ).open(wrapper.getPlayer());
    }

    @Command(
            type = CommandType.PLAYER,
            args = "click",
            perm = "mclibrary.click",
            desc = "클릭한 위치의 블럭 정보를 출력합니다."
    )
    public void blockInfo(CommandSenderWrapper wrapper, CommandArguments args) {
        Player player = wrapper.getPlayer();
        String message;
        if (!INFO_LISTENERS.contains(player)) {
            INFO_LISTENERS.add(player);
            message = "&aOn";
        } else {
            INFO_LISTENERS.remove(player);
            message = "&cOff";
        }
        wrapper.sendMessage(message);
    }

    @Command(
            args = "heal",
            perm = "mclibrary.heal",
            usage = "[플레이어]",
            desc = "체력과 허기를 회복합니다."
    )
    @SuppressWarnings("deprecation")
    public void heal(CommandSenderWrapper wrapper, CommandArguments args) {
        Player player = args.size() > 0 ? args.getPlayer(0) : wrapper.getPlayer();
        if (player != null) {
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(30);
            wrapper.sendMessage("완료.");
        } else {
            throw new InvalidUsageException(this, "올바른 플레이어명을 입력하세요.");
        }
    }
}
