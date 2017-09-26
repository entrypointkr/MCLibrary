package kr.rvs.mclibrary.bukkit.command.test;

import kr.rvs.mclibrary.bukkit.command.Command;
import kr.rvs.mclibrary.bukkit.command.SubCommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;

/**
 * Created by Junhyeong Lim on 2017-09-25.
 */
@Command(
        args = "test"
)
@SubCommand(TestCommand.TestSubCommand.class)
public class TestCommand {
    @Command(
            args = "a"
    )
    public void test(CommandSenderWrapper wrapper, VolatileArrayList args) {
        System.out.println("Invoke a");
    }

    @Command(
            args = "b"
    )
    class TestSubCommand {
        @Command(
                args = "c"
        )
        public void test(CommandSenderWrapper wrapper, VolatileArrayList args) {
            System.out.println("Invoke c");
        }
    }
}
