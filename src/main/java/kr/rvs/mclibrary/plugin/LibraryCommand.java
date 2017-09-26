//package kr.rvs.mclibrary.plugin;
//
//import kr.rvs.mclibrary.MCLibrary;
//import kr.rvs.mclibrary.bukkit.command.BaseCommand;
//import kr.rvs.mclibrary.bukkit.command.CommandType;
//import kr.rvs.mclibrary.bukkit.command.SubCommand;
//import kr.rvs.mclibrary.bukkit.inventory.event.GUIClickEvent;
//import kr.rvs.mclibrary.bukkit.inventory.gui.GUI;
//import kr.rvs.mclibrary.bukkit.inventory.gui.GUISignatureAdapter;
//import kr.rvs.mclibrary.bukkit.inventory.gui.handler.EventCancelHandler;
//import kr.rvs.mclibrary.bukkit.inventory.gui.handler.SpecificSlotHandler;
//import kr.rvs.mclibrary.bukkit.item.ItemBuilder;
//import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
//import kr.rvs.mclibrary.collection.VolatileArrayList;
//import org.bukkit.Material;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.entity.Creature;
//import org.bukkit.entity.Entity;
//import org.bukkit.event.inventory.InventoryType;
//
///**
// * Created by Junhyeong Lim on 2017-09-20.
// */
//public class LibraryCommand extends BaseCommand {
//    private final MCLibrary instance;
//
//    public LibraryCommand(MCLibrary instance) {
//        this.instance = instance;
//    }
//
//    @Override
//    public String label() {
//        return "mclibrary";
//    }
//
//    @Override
//    public SubCommand[] commands() {
//        return new SubCommand[] {
//                new ReloadCommand(),
//                new KillAllCommand(),
//                new GUICommand()
//        };
//    }
//
//    class ReloadCommand implements SubCommand {
//        @Override
//        public String args() {
//            return "reload";
//        }
//
//        @Override
//        public String perm() {
//            return "mclibrary.reload";
//        }
//
//        @Override
//        public void execute(CommandSenderWrapper wrapper, BaseCommand cmd, String label, VolatileArrayList args) {
//            FileConfiguration config = instance.getConfig();
//            instance.reloadConfig();
//            instance.configInit();
//            for (String key : config.getKeys(true)) {
//                wrapper.sendMessage(key + ": " + config.get(key));
//            }
//        }
//    }
//
//    class KillAllCommand implements SubCommand {
//        @Override
//        public String args() {
//            return "killall";
//        }
//
//        @Override
//        public CommandType type() {
//            return CommandType.PLAYER_ONLY;
//        }
//
//        @Override
//        public String perm() {
//            return "mclibrary.killall";
//        }
//
//        @Override
//        public void execute(CommandSenderWrapper wrapper, BaseCommand cmd, String label, VolatileArrayList args) {
//            wrapper.getPlayer().getWorld().getEntities().stream()
//                    .filter(entity -> entity instanceof Creature)
//                    .forEach(Entity::remove);
//        }
//    }
//
//    class GUICommand implements SubCommand {
//        @Override
//        public String args() {
//            return "gui";
//        }
//
//        @Override
//        public CommandType type() {
//            return CommandType.PLAYER_ONLY;
//        }
//
//        @Override
//        public String perm() {
//            return "mclibrary.gui";
//        }
//
//        @Override
//        public void execute(CommandSenderWrapper wrapper, BaseCommand cmd, String label, VolatileArrayList args) {
//            new GUI(
//                    new GUISignatureAdapter(InventoryType.CHEST)
//                            .title("MCLibrary GUI")
//                            .item(13, new ItemBuilder(Material.MAP).display("MCLibrary version").build()),
//                    new EventCancelHandler(),
//                    new SpecificSlotHandler(13) {
//                        @Override
//                        public void receive(GUIClickEvent e) {
//                            e.sendMessage(
//                                    "&aHello,",
//                                    "&e" + instance.getDescription().getFullName()
//                            );
//                        }
//                    }
//            ).open(wrapper.getPlayer());
//        }
//    }
//}
