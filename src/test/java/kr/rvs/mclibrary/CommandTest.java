package kr.rvs.mclibrary;

import kr.rvs.mclibrary.struct.command.CommandArgs;
import kr.rvs.mclibrary.struct.command.CommandProcessor;
import kr.rvs.mclibrary.struct.command.CommandType;
import kr.rvs.mclibrary.struct.command.MCCommand;
import kr.rvs.mclibrary.util.Injector;
import kr.rvs.mclibrary.util.MockFactory;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public class CommandTest extends Assert {
    @Before
    public void dependencyInject() {
        Injector.injectServer(MockFactory.createMockServer());
    }

    @Test
    public void commandTest() {
        SimpleCommandMap commandMap = new SimpleCommandMap(MockFactory.createMockServer());

        MCCommand command = new TestCommand();

        CommandProcessor processor = new CommandProcessor(
                command.label(),
                command.description(),
                command.usage(),
                Arrays.asList(command.aliases())
        );

        commandMap.register(processor.getLabel(), processor);

        commandMap.dispatch(MockFactory.createCommandSender(), "test a b c dfawef awef");
    }

    // TODO: Example command implemented class
    class TestCommand implements MCCommand {
        @Override
        public String label() {
            return "test";
        }


        @CommandArgs(
                type = CommandType.PLAYER_ONLY,
                args = "a b c",
                minArgs = 2
        )
        public void testCommand(CommandSender sender, VolatileArrayList list) {
            System.out.println("Test success");
        }
    }
}
