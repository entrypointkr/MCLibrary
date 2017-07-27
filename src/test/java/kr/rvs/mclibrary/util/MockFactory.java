package kr.rvs.mclibrary.util;

import kr.rvs.mclibrary.mock.MockItemFactory;
import kr.rvs.mclibrary.mock.MockItemMeta;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.plugin.SimplePluginManager;
import org.mockito.Mockito;

import java.util.logging.Logger;

import static org.mockito.BDDMockito.given;

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

        return server;
    }

    public static CommandSender createCommandSender() {
        CommandSender sender = mock(
                CommandSender.class,
                withSettings().extraInterfaces(Player.class)
        );
        return sender;
    }

    public static ItemFactory createItemFactory() {
        ItemFactory factory = mock(ItemFactory.class);
        when(factory.getItemMeta(any())).thenReturn(new MockItemMeta());

        return factory;
    }
}
