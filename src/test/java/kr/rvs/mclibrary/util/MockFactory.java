package kr.rvs.mclibrary.util;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.mockito.Mockito;

import java.util.logging.Logger;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class MockFactory {
    public static Server createMockServer() {
        Server server = Mockito.mock(Server.class);
        Mockito.when(server.getLogger()).thenReturn(Logger.getGlobal());
        Mockito.when(server.getPluginManager()).thenReturn(
                new SimplePluginManager(server, new SimpleCommandMap(server)));
        return server;
    }

    public static CommandSender createCommandSender() {
        CommandSender sender = Mockito.mock(CommandSender.class);
        return sender;
    }
}
