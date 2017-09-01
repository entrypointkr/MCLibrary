package kr.rvs.mclibrary.util;

import kr.rvs.mclibrary.mock.MockItemFactory;
import kr.rvs.mclibrary.mock.MockItemMeta;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFactory;
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
        when(server.createInventory(any(), anyInt(), anyString())).thenReturn(createInventory());

        return server;
    }

    public static Inventory createInventory() {
        Inventory inv = mock(Inventory.class);
        return inv;
    }

    public static CommandSender createCommandSender() {
        CommandSender sender = mock(
                CommandSender.class,
                withSettings().extraInterfaces(Player.class)
        );
        doAnswer(invocation -> {
            String fixed = StringUtils.join((String[]) invocation.getArguments()[0], '\n');
            System.out.println(ChatColor.stripColor(fixed));
            return null;
        }).when(sender).sendMessage((String[]) any());
        doAnswer(invocation -> {
            sender.sendMessage(new String[] {(String) invocation.getArguments()[0]});
            return null;
        }).when(sender).sendMessage(anyString());
        return sender;
    }

    public static Player createPlayer() {
        Player player = mock(Player.class);
        when(player.getOpenInventory()).thenReturn(createInventoryView());
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
        when(plugin.getName()).thenReturn("MCLibrary");

        return plugin;
    }
}
