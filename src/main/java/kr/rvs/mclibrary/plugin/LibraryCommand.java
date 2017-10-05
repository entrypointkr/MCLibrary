package kr.rvs.mclibrary.plugin;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandType;
import kr.rvs.mclibrary.bukkit.command.annotation.Command;
import kr.rvs.mclibrary.bukkit.command.exception.InvalidUsageException;
import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.EventCancelHandler;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.SpecificSlotHandler;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryType;

import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-09-20.
 */
@Command(
        args = "mclibrary"
)
public class LibraryCommand {
    private final MCLibrary instance = (MCLibrary) MCLibrary.getPlugin();

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
        if (wrapper.isPlayer()) {
            worldOpt = Optional.ofNullable(wrapper.getPlayer().getWorld());
        } else if (args.size() > 0) {
            worldOpt = args.getWorld(0);
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
                new GUISignatureAdapter(InventoryType.CHEST)
                        .title("MCLibrary GUI")
                        .item(13, new ItemBuilder(Material.MAP).display("MCLibrary version").build()),
                new EventCancelHandler(),
                new SpecificSlotHandler(13) {
                    @Override
                    public void receive(GUIClickEvent e) {
                        e.sendMessage(
                                "&aHello,",
                                "&e" + instance.getDescription().getFullName()
                        );
                    }
                }
        ).open(wrapper.getPlayer());
    }
}
