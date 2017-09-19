package kr.rvs.mclibrary.bukkit.command;

import kr.rvs.mclibrary.bukkit.command.internal.ICommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import kr.rvs.mclibrary.collection.VolatileArrayList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public abstract class BaseCommand {
    private final CommandCompiler compiler = new CommandCompiler();

    public abstract String label();

    public String description() {
        return "MCLibrary command";
    }

    public String usage() {
        return '/' + label();
    }

    public List<String> aliases() {
        return new ArrayList<>();
    }

    public BaseCommand addCommand(SubCommand... commands) {
        for (SubCommand command : commands) {
            compiler.addCommand(command);
        }
        return this;
    }

    public static void main(String[] args) {
        CommandCompiler compiler = new CommandCompiler();
        compiler.addCommand(new SubCommand() {
            @Override
            public String args() {
                return "awefawef awf";
            }

            @Override
            public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
                System.out.println(args.toString());
            }
        });
//        compiler.addCommand(
//                new SubCommand() {
//                    @Override
//                    public String args() {
//                        return "a b c";
//                    }
//
//                    @Override
//                    public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
//
//                    }
//                }
//        );
//        compiler.addCommand(
//                new SubCommand() {
//                    @Override
//                    public String args() {
//                        return "a b d";
//                    }
//
//                    @Override
//                    public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
//
//                    }
//                }
//        );
//        compiler.addCommand(
//                new SubCommand() {
//                    @Override
//                    public String args() {
//                        return "c a d";
//                    }
//
//                    @Override
//                    public void execute(CommandSenderWrapper sender, String label, VolatileArrayList args) {
//
//                    }
//                }
//        );
        ICommand command = compiler.getCommand();
        command.execute(null, "test", new ArrayDeque<>(Arrays.asList("awefawef", "aw")));
    }
}
