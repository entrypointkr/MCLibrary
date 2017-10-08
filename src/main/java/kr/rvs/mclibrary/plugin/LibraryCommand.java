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

import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
@Command(
        args = "mclibrary"
)
public class LibraryCommand {
    private static final EntityHashSet<Player> blockInfoPlayers = new EntityHashSet<>();
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
                if (block == null || !blockInfoPlayers.contains(player))
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
        Optional<World> worldOpt;
        if (args.size() > 0) {
            worldOpt = args.getWorld(0);
        } else if (wrapper.isPlayer()) {
            worldOpt = Optional.ofNullable(wrapper.getPlayer().getWorld());
        } else {
            throw new InvalidUsageException(this, "&c월드 이름을 입력하세요.");
        }

        if (worldOpt.isPresent()) {
            worldOpt.get().getEntities().stream()
                    .filter(entity -> entity instanceof Creature)
                    .forEach(Entity::remove);
        } else {
            wrapper.sendMessage("알 수 없는 월드입니다.");
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
        if (!blockInfoPlayers.contains(player)) {
            blockInfoPlayers.add(player);
            message = "&aOn";
        } else {
            blockInfoPlayers.remove(player);
            message = "&cOff";
        }
        wrapper.sendMessage(message);
    }
}
