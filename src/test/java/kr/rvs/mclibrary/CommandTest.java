package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.command.Command;
import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandManager;
import kr.rvs.mclibrary.bukkit.command.TabCompletor;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import kr.rvs.mclibrary.struct.Injector;
import kr.rvs.mclibrary.struct.MockFactory;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandTest extends Assert {
    private final CommandMap commandMap = new SimpleCommandMap(MockFactory.createMockServer());
    private final CommandManager manager = new CommandManager(commandMap);
    private final CommandSender mockSender = MockFactory.createCommandSender();
    private static final AtomicBoolean storage = new AtomicBoolean(false);

    @Before
    public void register() throws NoSuchMethodException {
        Injector.injectServer(MockFactory.createMockServer());
        Injector.injectPlugin(MockFactory.createPlugin());
        manager.registerCommand(TestCommand.class, MockFactory.createPlugin());
    }

    @Test
    public void commandTest() throws InterruptedException {
        commandMap.dispatch(mockSender, "test ab");

        if (!storage.get()) {
            throw new Error("Command testing fail");
        }
    }

    @Test
    public void commandHelp() {
        // Help message
        commandMap.dispatch(mockSender, "test");
        commandMap.dispatch(mockSender, "test a b c");
    }

    @Test
    public void tabComplete() {
        List<String> matches = commandMap.tabComplete(mockSender, "test a");
        List<String> matchesB = commandMap.tabComplete(mockSender, "test abc");

        assertEquals(matches, Arrays.asList("abc"));
        assertEquals(matchesB, Arrays.asList("test", "completes"));
    }

    @Command(
            args = "test"
    )
    static class TestCommand {
        @Command(
                args = "ab"
        )
        public void test(CommandSenderWrapper wrapper, CommandArguments args) {
            storage.set(true);
        }

        @TabCompletor(
                args = "abc"
        )
        public List<String> tab(CommandSenderWrapper wrapper, CommandArguments args) {
            return Arrays.asList("test", "completes");
        }
    }
}
