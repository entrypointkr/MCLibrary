package kr.rvs.mclibrary.util;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
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
        return server;
    }

    public static CommandSender createCommandSender() {
        CommandSender sender = mock(
                CommandSender.class,
                withSettings().extraInterfaces(Player.class)
        );
        return sender;
    }
}
