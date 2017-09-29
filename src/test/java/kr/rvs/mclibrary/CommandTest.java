package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.command.*;
import kr.rvs.mclibrary.bukkit.command.annotation.Command;
import kr.rvs.mclibrary.bukkit.command.annotation.TabCompleter;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandTest extends Assert {
    private final CommandMap commandMap = new SimpleCommandMap(MockFactory.createMockServer());
    private final CommandManager manager = new CommandManager(commandMap);
    private final CommandSender mockSender = MockFactory.createCommandSender();
    private static final AtomicInteger integer = new AtomicInteger();

    @Before
    public void register() throws NoSuchMethodException {
        Injector.injectServer(MockFactory.createMockServer());
        Injector.injectPlugin(MockFactory.createPlugin());
        manager.registerCommand(TestCommand.class, MockFactory.createPlugin());
    }

    @Test
    public void commandTest() throws InterruptedException {
        commandMap.dispatch(mockSender, "test abc");
        commandMap.dispatch(mockSender, "test a b c d e f 1 2");

        if (integer.get() < 3) {
            throw new Error("Command testing fail");
        }
    }

    @Test
    public void commandHelp() {
        // Help message
        commandMap.dispatch(mockSender, "test help");
        commandMap.dispatch(mockSender, "test a b c");
    }

    @Test
    public void tabComplete() {
        List<String> matches = commandMap.tabComplete(mockSender, "test ab");
        List<String> matchesB = commandMap.tabComplete(mockSender, "test abc");

        assertEquals(matches, Arrays.asList("abc"));
        assertEquals(matchesB, Arrays.asList("test", "completes"));
    }

    @Command(
            args = "test"
    )
    static class TestCommand {
        public TestCommand(CommandAdaptor adaptor) {
            integer.incrementAndGet();
        }

        @Command(
                args = "abc",
                usage = "[test]"
        )
        public void test(CommandSenderWrapper wrapper, CommandArguments args) {
            integer.incrementAndGet();
        }

        @Command(
                args = "a b c d e f",
                usage = "(num1) (num2)",
                desc = "Test description",
                min = 2,
                max = 2
        )
        public void test(CommandSenderWrapper wrapper, List<String> args) {
            if (args.equals(Arrays.asList("1", "2")))
                integer.incrementAndGet();
        }

        @TabCompleter(
                args = "abc"
        )
        public List<String> tab(CommandSenderWrapper wrapper, CommandArguments args) {
            return Arrays.asList("test", "completes");
        }
    }
}
