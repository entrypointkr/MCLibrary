package kr.rvs.mclibrary;

import kr.rvs.mclibrary.bukkit.command.BaseCommand;
import kr.rvs.mclibrary.bukkit.command.SubCommand;
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
        BaseCommand command = new BaseCommand() {
            @Override
            public String label() {
                return "test";
            }
        }.addCommand(new SubCommand() {
            @Override
            public String args() {
                return "a b cd";
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
                System.out.println("args: " + args);
                latch.countDown();
            }
        });
        CommandProcessor processor = new CommandProcessor(command, MockFactory.createPlugin());
        commandMap.register(processor.getLabel(), processor);
    }

    @Test
    public void commandTest() throws InterruptedException {
        commandMap.dispatch(mockSender, "test a b cd a b c");

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
        List<String> matches = commandMap.tabComplete(mockSender, "test a b c");
        System.out.println(matches.toString());
        assertTrue(matches.contains("cd"));
    }
}
