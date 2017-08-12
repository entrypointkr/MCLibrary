package kr.rvs.mclibrary.struct.command.layout;

import kr.rvs.mclibrary.struct.command.CommandStorage;
import kr.rvs.mclibrary.struct.command.MCCommand;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-08-10.
 */
public abstract class PagingCommandLayout implements CommandLayout {
    private static final int PAGE_LINE = 5;

    @Override
    public void writeHelpMessage(StringBuilder builder, MCCommand parent, List<CommandStorage> storages, VolatileArrayList args) {
        int page = args.getInt(1, 1);
        int pages = getPageSize(storages);
        int index = (page - 1) * PAGE_LINE;

        builder.append(prefix(parent, page, pages))
                .append('\n');
        builder.append(usage(parent, page, pages))
                .append('\n');
        for (int i = index; i < index + PAGE_LINE; i++) {
            if (storages.size() <= i)
                break;

            builder.append('\n');
            writeCmdInfoLine(parent, builder, storages.get(i));
        }
        String suffix = suffix(parent, page, pages);
        if (suffix != null && !suffix.isEmpty())
            builder.append(suffix);
    }

    private int getPageSize(List<CommandStorage> storages) {
        int size = storages.size();
        int pages = size / PAGE_LINE;
        if (size % PAGE_LINE != 0)
            pages++;

        return pages;
    }

    public abstract String prefix(MCCommand parent, int currPage, int maxPage);

    public abstract String usage(MCCommand parent, int currPage, int maxPage);

    public abstract void writeCmdInfoLine(MCCommand parent, StringBuilder builder, CommandStorage storage);

    public abstract String suffix(MCCommand parent, int currPage, int maxPag);
}
