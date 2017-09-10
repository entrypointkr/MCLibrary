package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.bukkit.command.*;
import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
import kr.rvs.mclibrary.bukkit.inventory.handler.EventCancelHandler;
import kr.rvs.mclibrary.bukkit.inventory.handler.SpecificSlotHandler;
import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
import kr.rvs.mclibrary.bukkit.protocol.PacketMonitoringListener;
import kr.rvs.mclibrary.general.Version;
import kr.rvs.mclibrary.gson.GsonManager;
import kr.rvs.mclibrary.gson.SettingManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

// TODO: 서브커맨드 도움말, 직렬화

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MCLibrary extends JavaPlugin {
    private static final CommandManager commandManager = new CommandManager();
    private static final GsonManager gsonManager = new GsonManager();
    private static final SettingManager settingManager = new SettingManager();
    private static final Version bukkitVersion = new Version(Bukkit.getBukkitVersion());
    private static MCLibrary plugin;

    public MCLibrary() {
        plugin = this;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static GsonManager getGsonManager() {
        return gsonManager;
    }

    public static SettingManager getSettingManager() {
        return settingManager;
    }

    public static Version getBukkitVersion() {
        return bukkitVersion;
    }

    public static MCLibrary getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        GUI.init(this);
        saveDefaultConfig();
        getCommandManager().registerCommand(new LibraryCommand(), this);

        getConfig().options().copyDefaults(true);
        configInit();
    }

    private void configInit() {
        if (MCUtils.isEnabled("ProtocolLib")
                && getConfig().getBoolean(ConfigKeys.PACKET_DEBUG, false)) {
            MCUtils.getProtocolManager().removePacketListeners(this);
            MCUtils.getProtocolManager().addPacketListener(new PacketMonitoringListener());
        }
    }

    @Override
    public void onDisable() {
        settingManager.save();
        saveConfig();
    }

    @CommandIgnore
    public class LibraryCommand implements MCCommand {
        @Override
        public String label() {
            return "mclibrary";
        }

        @CommandArgs(
                args = "reload",
                desc = "Reload config file"
        )
        public void onReload(CommandSender sender, List<String> args) {
            reloadConfig();
            configInit();
            for (String key : getConfig().getKeys(true)) {
                sender.sendMessage(key + ": " + getConfig().get(key));
            }
        }

        @CommandArgs(
                type = CommandType.PLAYER_ONLY,
                perm = "mclibrary.killall",
                args = "killall",
                desc = "Kill all entities"
        )
        public void onKillall(CommandSenderWrapper sender, List<String> args) {
            sender.getPlayer().getWorld().getEntities()
                    .stream()
                    .filter(entity -> entity instanceof Creature)
                    .forEach(Entity::remove);
        }

        @CommandArgs(
                type = CommandType.PLAYER_ONLY,
                perm = "mclibrary.gui",
                args = "gui",
                desc = "Open a gui"
        )
        public void onGui(CommandSenderWrapper sender, List<String> args) {
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
                                    "&e" + getDescription().getFullName()
                            );
                        }
                    }
            ).open(sender.getPlayer());
        }
    }
}
