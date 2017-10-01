package kr.rvs.mclibrary.plugin;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandType;
import kr.rvs.mclibrary.bukkit.command.annotation.Command;
import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.EventCancelHandler;
import kr.rvs.mclibrary.bukkit.inventory.gui.handler.SpecificSlotHandler;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryType;

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
            perm = "mclibrary.reload"
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
            type = CommandType.PLAYER,
            args = "killall",
            perm = "mclibrary.killall"
    )
    public void killallCommand(CommandSenderWrapper wrapper, CommandArguments args) {
        wrapper.getPlayer().getWorld().getEntities().stream()
                .filter(entity -> entity instanceof Creature)
                .forEach(Entity::remove);
    }

    @Command(
            args = "gui",
            perm = "mclibrary.gui"
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
