package kr.rvs.mclibrary.bukkit.command.duplex;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandInfo;
import kr.rvs.mclibrary.bukkit.command.ICommand;
import kr.rvs.mclibrary.bukkit.player.CommandSenderWrapper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-09-28.
 */
public class HelpExecutor implements ICommand {
    private final ICommand command;
    private final String label;
    private final int line;
    private final List<CommandStorage> storageList = new ArrayList<>();
    private final String helpArg;
    private int lastHashCode = -1;

    public HelpExecutor(ICommand command, String label, int line) {
        this.command = command;
        this.label = label;
        this.line = line;
        this.helpArg = label.matches("[a-zA-Z]+") ? "help" : "도움말";

        if (command instanceof ComplexCommand) {
            ComplexCommand compositeExecutor = (ComplexCommand) command;
            if (!compositeExecutor.containsKey(helpArg))
                compositeExecutor.put(helpArg, this);
        }
    }

    private void checkDiff() {
        int hashCode = command.hashCode();
        if (lastHashCode != hashCode) {
            storageList.clear();
            init("", command);
            lastHashCode = hashCode;
        }
    }

    private String argSpacing(String args, String key) {
        return args.isEmpty() ? key : key.isEmpty() ? args : args + ' ' + key;
    }

    private void init(String args, ICommand command) {
        if (command instanceof ComplexCommand) {
            ComplexCommand compositeExecutor = (ComplexCommand) command;

            for (Map.Entry<String, ICommand> entry : compositeExecutor.entrySet()) {
                String key = entry.getKey();
                ICommand val = entry.getValue();

                if (val instanceof ComplexCommand) {
                    ComplexCommand complexCommand = (ComplexCommand) val;
                    ICommand absolute = complexCommand.getAbsoluteCommand();
                    init(argSpacing(args, key), val);
                    if (absolute != null)
                        init(argSpacing(args, ""), absolute);
                } else if (val instanceof CommandInfo) {
                    CommandStorage storage = new CommandStorage(argSpacing(args, key), (CommandInfo) val);
                    storageList.add(storage);
                }
            }
        } else if (command instanceof CommandInfo) {
            CommandStorage storage = new CommandStorage("", (CommandInfo) command);
            storageList.add(storage);
        }
    }

    private int getMaxPage() {
        return storageList.size() / line + (storageList.size() % line > 0 ? 1 : 0);
    }

    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
        checkDiff();

        int maxPage = getMaxPage();
        int currPage = Math.min(Math.max(args.getInt(0, 1), 1), maxPage);
        int start = (currPage - 1) * line;
        int end = Math.min(currPage * line, storageList.size());
        boolean paging = maxPage > 1;

        // Header
        StringBuilder header = new StringBuilder().append("&e--------- &f도움말: /").append(label).append(' ');
        if (paging) {
            header.append(String.format("(%d/%d) ", currPage, maxPage));
        }
        header.append("&e");
        for (int i = header.length(); i < 55; i++) {
            header.append('-');
        }
        wrapper.sendMessage(header);

        // Additional
        if (paging) {
            wrapper.sendMessage(String.format(
                    "&7'/%s %s [페이지]' 를 입력할 수 있습니다.",
                    label, helpArg
            ));
        } else {
            wrapper.sendMessage("&7하위 명령어 목록");
        }

        // Contents
        for (int i = start; i < end; i++) {
            CommandStorage storage = storageList.get(i);
            sendCommandInfo(wrapper, storage.args, storage.info);
        }
    }

    @Override
    public List<String> tabComplete(CommandSenderWrapper wrapper, CommandArguments args) {
        checkDiff();

        List<String> ret = new ArrayList<>();
        int maxPage = getMaxPage();
        for (int i = 1; i <= maxPage; i++) {
            ret.add(String.valueOf(i));
        }
        return ret;
    }

    public void sendCommandInfo(CommandSenderWrapper wrapper, String args, CommandInfo commandInfo) {
        CommandSender sender = wrapper.getSender();
        String usage = commandInfo.usage();
        String perm = commandInfo.perm();
        boolean hasPerm = StringUtils.isEmpty(perm) || sender.hasPermission(perm);
        ChatColor color = hasPerm ? ChatColor.GOLD : ChatColor.RED;
        String desc = commandInfo.desc();
        StringBuilder builder = new StringBuilder()
                .append(color).append('/').append(label);

        if (StringUtils.isNotEmpty(args))
            builder.append(' ').append(args);
        if (StringUtils.isNotEmpty(usage))
            builder.append(' ').append(usage);
        if (StringUtils.isNotEmpty(desc))
            builder.append(": ").append(ChatColor.WHITE).append(desc);

        wrapper.sendMessage(builder);
    }

//    @Override
//    public String usage() {
//        return "[페이지]";
//    }
//
//    @Override
//    public String desc() {
//        return "명령어 도움말을 봅니다.";
//    }
//
//    @Override
//    public String perm() {
//        return null;
//    }

    static class CommandStorage {
        private final String args;
        private final CommandInfo info;

        public CommandStorage(String args, CommandInfo info) {
            this.args = args;
            this.info = info;
        }
    }
}
