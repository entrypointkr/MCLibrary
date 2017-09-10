package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.command.CommandArgs;
import kr.rvs.mclibrary.bukkit.command.CommandProcessor;
import kr.rvs.mclibrary.bukkit.command.CommandType;
import kr.rvs.mclibrary.bukkit.command.MCCommand;
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

        MCCommand command = new TestCommand();

        CommandProcessor processor = new CommandProcessor(
                command.label(),
                command.description(),
                command.usage(),
                Arrays.asList(command.aliases()),
                MockFactory.createPlugin(),
                command
        );

        commandMap.register(processor.getLabel(), processor);
    }

    @Test
    public void commandTest() throws InterruptedException {
        commandMap.dispatch(mockSender, "test a b cef dfawef awef");

        if (!latch.await(10, TimeUnit.SECONDS)) {
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
        List<String> matches = commandMap.tabComplete(mockSender, "test a b ce");
        System.out.println(matches.toString());
        assertTrue(matches.size() == 2);
    }

    public class TestCommand implements MCCommand {
        @Override
        public String label() {
            return "test";
        }

        @CommandArgs(
                type = CommandType.PLAYER_ONLY,
                args = "a b cef",
                min = 2
        )
        public void testCommand(CommandSender sender, VolatileArrayList list) {
            System.out.println("Test success");
            latch.countDown();
        }

        @CommandArgs(
                type = CommandType.PLAYER_ONLY,
                args = "a b cea"
        )
        public void testCommandB(CommandSender sender, VolatileArrayList args) {
        }

        @CommandArgs(
                args = "aa"
        )
        public void helpTest(CommandSender sender, VolatileArrayList args) {
        }

        @CommandArgs(
                args = "b"
        )
        public void helpTestB(CommandSender sender, VolatileArrayList args) {
        }

        @CommandArgs(
                args = "c"
        )
        public void helpTestC(CommandSender sender, VolatileArrayList args) {
        }

        @CommandArgs(
                args = "d"
        )
        public void helpTestD(CommandSender sender, VolatileArrayList args) {
        }

        @CommandArgs(
                args = "e"
        )
        public void helpTestE(CommandSender sender, VolatileArrayList args) {
        }

        @CommandArgs(
                args = "f"
        )
        public void helpTestF(CommandSender sender, VolatileArrayList args) {
        }

        @CommandArgs(
                args = "g"
        )
        public void helpTestG(CommandSender sender, VolatileArrayList args) {
        }
    }
}
