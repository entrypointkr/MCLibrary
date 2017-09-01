package kr.rvs.mclibrary.struct.command.layout;

import kr.rvs.mclibrary.struct.command.CommandStorage;
import kr.rvs.mclibrary.struct.command.MCCommand;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;
import kr.rvs.mclibrary.util.general.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Created by Junhyeong Lim on 2017-08-10.
 */
public abstract class PagingCommandLayout implements CommandLayout {
    private static final int PAGE_LINE = 8;

    @Override
    public void writeHelpMessage(CommandLayoutStorage storage) {
        StringBuilder builder = storage.getBuilder();
        VolatileArrayList args = storage.getArgs();
        List<CommandStorage> storages = storage.getStorages();

        int page = args.size() == 1 ? args.getInt(0, 1) : args.getInt(1, 1);
        int pages = getPageSize(storages);
        int index = (page - 1) * PAGE_LINE;

        String prefix = prefix(storage, page, pages);
        String usage = usage(storage, page, pages);
        String suffix = suffix(storage, page, pages);

        if (isNotEmpty(prefix))
            builder.append(prefix).append('\n');
        if (isNotEmpty(usage))
            builder.append(usage).append('\n');

        if (isPagingRequire(pages, storage.getSender())) {
            for (int i = index; i < index + PAGE_LINE; i++) {
                if (storages.size() <= i) // IndexOverflow
                    break;

                if (i != index)
                    builder.append('\n');
                writeCmdInfoLine(storage, storages.get(i));
            }
        } else {
            for (int i = 0; i < storages.size(); i++) {
                if (i != 0)
                    builder.append('\n');
                writeCmdInfoLine(storage, storages.get(i));
            }
        }

        if (isNotEmpty(suffix))
            builder.append(suffix);
    }

    public boolean isPagingRequire(int maxPage, CommandSender sender) {
        return maxPage > 1 && (!(sender instanceof ConsoleCommandSender));
    }

    private int getPageSize(List<CommandStorage> storages) {
        int size = storages.size();
        int pages = size / PAGE_LINE;
        if (size % PAGE_LINE != 0)
            pages++;

        return pages;
    }

    public String prefix(CommandLayoutStorage storage, int currPage, int maxPage) {
        StringBuilder builder = new StringBuilder(50)
                .append("&e--------- ").append("&f도움말: /").append(storage.getParent().label()).append(' ');

        if (isPagingRequire(maxPage, storage.getSender()))
            builder.append(String.format("(%d/%d)", currPage, maxPage));

        builder.append("&e");
        for (int i = StringUtil.length(builder.toString()); i < 55; i++)
            builder.append('-');

        return builder.toString();
    }

    public String usage(CommandLayoutStorage storage, int currPage, int maxPage) {
        MCCommand parent = storage.getParent();
        return String.format("&7'/%s %s [페이지]' 를 입력할 수 있습니다.",
                parent.label(), parent.helpArg());
    }

    public abstract void writeCmdInfoLine(CommandLayoutStorage layoutStorage, CommandStorage storage);

    public String suffix(CommandLayoutStorage storage, int currPage, int maxPag) {
        return null;
    }
}
