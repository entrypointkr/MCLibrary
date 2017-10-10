package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandManager;
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
        commandMap.dispatch(mockSender, "test first");
        commandMap.dispatch(mockSender, "test second");
        commandMap.dispatch(mockSender, "test first b 10");
        commandMap.dispatch(mockSender, "test second b");

        if (integer.get() < 4) {
            throw new Error("Command testing fail");
        }
    }

    @Test
    public void commandHelp() {
        // Help message
        commandMap.dispatch(mockSender, "test");
        commandMap.dispatch(mockSender, "test help 2");
    }

    @Test
    public void tabComplete() {
        List<String> matches = commandMap.tabComplete(mockSender, "test fi");
        List<String> matchesB = commandMap.tabComplete(mockSender, "test first ");

        assertEquals(matches, Arrays.asList("first"));
        assertEquals(matchesB, Arrays.asList("test", "completes"));
    }

    @Command(
            args = "test"
    )
    static class TestCommand {
        @Command(
                args = "first"
        )
        public void execute1(CommandSenderWrapper wrapper, CommandArguments args) {
            wrapper.sendMessage("Example command 1");
            integer.incrementAndGet();
        }

        @TabCompleter(
                args = "first"
        )
        public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) {
            return Arrays.asList("test", "completes");
        }

        @Command(
                args = "second",
                desc = "Test description"
        )
        public void execute2(CommandSenderWrapper wrapper, CommandArguments args) {
            wrapper.sendMessage("Example command 2");
            integer.incrementAndGet();
        }

        @Command(
                args = "first b",
                usage = "(number)",
                desc = "Test description",
                min = 1
        )
        public void execute3(CommandSenderWrapper wrapper, CommandArguments args) {
            int defaultNumber = 1;
            int arg = args.getInt(0, defaultNumber);
            wrapper.sendMessage("Example command 3 " + arg);
            integer.incrementAndGet();
        }

        @Command(
                args = "second"
        )
        static class TestSubCommand {
            @Command(
                    args = "b"
            )
            public void execute4(CommandSenderWrapper wrapper, CommandArguments args) {
                wrapper.sendMessage("Example command 4");
                integer.incrementAndGet();
            }
        }

        @Command(
                args = "first c"
        )
        public void execute5(CommandSenderWrapper wrapper, CommandArguments args) {

        }

        @Command(
                args = "first d"
        )
        public void execute6(CommandSenderWrapper wrapper, CommandArguments args) {

        }

        @Command(
                args = "first e"
        )
        public void execute7(CommandSenderWrapper wrapper, CommandArguments args) {

        }

        @Command(
                args = "first f"
        )
        public void execute8(CommandSenderWrapper wrapper, CommandArguments args) {

        }

        @Command(
                args = "first g"
        )
        public void execute9(CommandSenderWrapper wrapper, CommandArguments args) {

        }
    }
}
