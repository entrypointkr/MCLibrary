package kr.rvs.mclibrary.struct;

import kr.rvs.mclibrary.MCLibrary;
import kr.rvs.mclibrary.mock.MockInventory;
import kr.rvs.mclibrary.mock.MockItemFactory;
import kr.rvs.mclibrary.mock.MockItemMeta;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.mockito.Mockito;

import java.util.logging.Logger;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MockFactory extends Mockito {
    public static Server createMockServer() {
        Server server = mock(Server.class);
        when(server.getLogger()).thenReturn(Logger.getGlobal());
        when(server.getPluginManager()).thenReturn(
                new SimplePluginManager(server, new SimpleCommandMap(server)));
        when(server.getItemFactory()).thenReturn(new MockItemFactory());
        doAnswer(invocation -> new MockInventory(InventoryType.CHEST, invocation.getArgument(1), invocation.getArgument(2)))
                .when(server).createInventory(any(), anyInt(), anyString());
        when(server.getBukkitVersion()).thenReturn("1.0");

        return server;
    }

    public static CommandSender createCommandSender() {
        Server server = createMockServer();
        CommandSender sender = mock(
                CommandSender.class,
                withSettings().extraInterfaces(Player.class)
        );
        when(sender.getServer()).thenReturn(server);
        doAnswer(invocation -> {
            String fixed = StringUtils.join((String[]) invocation.getArguments()[0], '\n');
            System.out.println(ChatColor.stripColor(fixed));
            return null;
        }).when(sender).sendMessage(any(String[].class));
        doAnswer(invocation -> {
            sender.sendMessage(new String[]{(String) invocation.getArguments()[0]});
            return null;
        }).when(sender).sendMessage(anyString());
        return sender;
    }

    public static Player createPlayer() {
        Player player = mock(Player.class);
        when(player.getOpenInventory()).thenReturn(createInventoryView());
        doAnswer(invocation -> {
            Inventory inv = (Inventory) invocation.getArguments()[0];
            System.out.println("Title: \"" + inv.getTitle() + "\", Size:" + inv.getSize());
            StringBuilder builder = new StringBuilder(inv.getSize() * 9);
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = inv.getItem(i);
                String id = item != null ? String.valueOf(item.getTypeId()) : "X";
                builder.append(id);
                for (int a = 0; a < 4 - id.length(); a++)
                    builder.append(' ');

                if ((i + 1) % 9 == 0)
                    builder.append('\n');
                else
                    builder.append(' ');
            }
            System.out.println(builder.toString());
            return null;
        }).when(player).openInventory(any(Inventory.class));
        return player;
    }

    public static InventoryView createInventoryView() {
        InventoryView view = mock(InventoryView.class);
        return view;
    }

    public static ItemFactory createItemFactory() {
        ItemFactory factory = mock(ItemFactory.class);
        when(factory.getItemMeta(any())).thenReturn(new MockItemMeta());

        return factory;
    }

    public static Plugin createPlugin() {
        Plugin plugin = mock(Plugin.class);
        YamlConfiguration config = new YamlConfiguration();
        config.set(MCLibrary.DETAIL_LOG, true);
        when(plugin.getName()).thenReturn("MCLibrary");
        when(plugin.getConfig()).thenReturn(config);

        return plugin;
    }
}
