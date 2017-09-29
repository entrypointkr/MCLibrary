package kr.rvs.mclibrary.bukkit.command.executor;

import kr.rvs.mclibrary.bukkit.command.CommandArguments;
import kr.rvs.mclibrary.bukkit.command.CommandExecutable;
import kr.rvs.mclibrary.bukkit.command.CommandInfo;
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
public class HelpExecutor implements CommandExecutable, CommandInfo {
    private final CommandExecutable executable;
    private final String label;
    private final int line;
    private final List<CommandStorage> storageList = new ArrayList<>();
    private final String helpArg;
    private int lastHashCode = -1;

    public HelpExecutor(CommandExecutable executable, String label, int line) {
        this.executable = executable;
        this.label = label;
        this.line = line;
        this.helpArg = StringUtils.isAlphanumeric(label) ? "help" : "도움말";
    }

    private void checkDiff() {
        int hashCode = executable.hashCode();
        if (lastHashCode != hashCode) {
            storageList.clear();
            init("", executable);
            lastHashCode = hashCode;

            if (executable instanceof CompositeExecutor) {
                CompositeExecutor compositeExecutor = (CompositeExecutor) executable;
                if (!compositeExecutor.containsKey(helpArg))
                    compositeExecutor.put(helpArg, this);
            }
        }
    }

    private void init(String args, CommandExecutable commandExecutable) {
        if (commandExecutable instanceof CompositeExecutor) {
            CompositeExecutor compositeExecutor = (CompositeExecutor) commandExecutable;

            for (Map.Entry<String, CommandExecutable> entry : compositeExecutor.entrySet()) {
                String key = entry.getKey();
                CommandExecutable val = entry.getValue();

                if (val instanceof CompositeExecutor) {
                    init(args + key + ' ', val);
                } else if (val instanceof CommandInfo) {
                    CommandStorage storage = new CommandStorage(args + key, (CommandInfo) val);
                    storageList.add(storage);
                }
            }
        } else if (commandExecutable instanceof CommandInfo) {
            CommandStorage storage = new CommandStorage("", (CommandInfo) commandExecutable);
            storageList.add(storage);
        }
    }

    @Override
    public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
        checkDiff();

        int currPage = Math.max(args.getInt(0, 1), 1);
        int maxPage = storageList.size() / line + storageList.size() % line > 0 ? 1 : 0;
        int start = (currPage - 1) * line;
        int end = Math.min(currPage * line, storageList.size());

        StringBuilder header = new StringBuilder()
                .append(String.format("&e--------- &f도움말: /%s (%d/%d) &6", label, currPage, maxPage));
        for (int i = header.length(); i < 55; i++) {
            header.append('-');
        }
        wrapper.sendMessage(header);

        for (int i = start; i < end; i++) {
            CommandStorage storage = storageList.get(i);
            sendCommandInfo(wrapper, storage.args, storage.info);
        }
    }

    public void sendCommandInfo(CommandSenderWrapper wrapper, String args, CommandInfo commandInfo) {
        CommandSender sender = wrapper.getSender();
        String usage = commandInfo.usage();
        String perm = commandInfo.perm();
        boolean hasPerm = StringUtils.isEmpty(perm) || sender.hasPermission(perm);
        ChatColor color = hasPerm ? ChatColor.YELLOW : ChatColor.RED;
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

    @Override
    public String usage() {
        return "[페이지]";
    }

    @Override
    public String desc() {
        return "명령어 도움말을 봅니다.";
    }

    @Override
    public String perm() {
        return null;
    }

    static class CommandStorage {
        private final String args;
        private final CommandInfo info;

        public CommandStorage(String args, CommandInfo info) {
            this.args = args;
            this.info = info;
        }
    }
}
