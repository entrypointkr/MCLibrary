package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import kr.rvs.mclibrary.bukkit.command.SubCommand;
import kr.rvs.mclibrary.bukkit.command.TabCompletable;
import kr.rvs.mclibrary.bukkit.command.internal.CommandProcessor;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;
import kr.rvs.mclibrary.struct.Injector;
import kr.rvs.mclibrary.struct.MockFactory;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandTest extends Assert {
    private final SimpleCommandMap commandMap = new SimpleCommandMap(MockFactory.createMockServer());
    private final CommandSender mockSender = MockFactory.createCommandSender();
    private final CountDownLatch latch = new CountDownLatch(1);

    @Before
    public void register() throws NoSuchMethodException {
        Injector.injectServer(MockFactory.createMockServer());
        BaseCommand command = new TestCommand().addCommands(
                new TestSubCommand(),
                new TabCompleteTestSubCommand()
        );
        CommandProcessor processor = new CommandProcessor(command, MockFactory.createPlugin());
        commandMap.register(processor.getLabel(), processor);
    }

    @Test
    public void commandTest() throws InterruptedException {
        commandMap.dispatch(mockSender, "test a b c 1 2 3");

        if (!latch.await(3, TimeUnit.SECONDS)) {
            throw new Error("Command testing fail");
        }
    }

    @Test
    public void commandHelp() {
        // Help message
        commandMap.dispatch(mockSender, "test awe faw");
        commandMap.dispatch(mockSender, "test help 2");
    }

    @Test
    public void tabComplete() {
        List<String> matches = commandMap.tabComplete(mockSender, "test a b ");
        List<String> matchesB = commandMap.tabComplete(mockSender, "test a b d");

        assertEquals(matches, Arrays.asList("c", "d"));
        assertEquals(matchesB, Arrays.asList("test", "completes"));
    }

    class TestCommand extends BaseCommand {
        @Override
        public String label() {
            return "test";
        }
    }

    class TestSubCommand implements SubCommand {
        @Override
        public String args() {
            return "a b c";
        }

        @Override
        public int min() {
            return 3;
        }

        @Override
        public int max() {
            return 3;
        }

        @Override
        public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
            latch.countDown();
        }
    }

    class TabCompleteTestSubCommand implements SubCommand, TabCompletable {
        @Override
        public String args() {
            return "a b d";
        }

        @Override
        public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {

        }

        @Override
        public List<String> tabComplete(CommandSenderWrapper sender, String label, VolatileArrayList args) throws IllegalArgumentException {
            return Arrays.asList("test", "completes");
        }
    }
}
