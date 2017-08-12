package kr.rvs.mclibrary.struct.command.layout;

import kr.rvs.mclibrary.struct.command.CommandStorage;
import kr.rvs.mclibrary.struct.command.MCCommand;

/**
 * Created by Junhyeong Lim on 2017-08-10.
 */
public class DefaultCommandLayout extends PagingCommandLayout {
    @Override
    public String prefix(MCCommand parent, int currPage, int maxPage) {
        String front = "&e--------- ";
        String back = " &e---------------------";
        String logo = "&f도움말: /" + parent.label();

        return maxPage <= 1 ?
                front + logo + back :
                front + logo + String.format(" (%d/%d)", currPage, maxPage) + back;
    }

    @Override
    public String usage(MCCommand parent, int currPage, int maxPage) {
        return String.format("&7'/%s %s [페이지]' 를 입력할 수 있습니다.",
                parent.label(), parent.helpArg());
    }

    @Override
    public void writeCmdInfoLine(MCCommand parent, StringBuilder builder, CommandStorage storage) {
        builder.append("&6/").append(parent.label());
        if (storage.hasArgs())
            builder.append(" ").append(storage.getArgs());
        if (storage.hasUsage())
            builder.append(" ").append(storage.getUsage());
        builder.append(": &f");
        if (storage.hasDescription())
            builder.append(storage.getDescription());
        else
            builder.append("'/").append(parent.label()).append("' 의 하위 명령어");
    }

    @Override
    public String suffix(MCCommand parent, int currPage, int maxPag) {
        return null;
    }
}
