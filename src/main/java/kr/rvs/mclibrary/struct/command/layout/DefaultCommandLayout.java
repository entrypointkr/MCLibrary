package kr.rvs.mclibrary.struct.command.layout;

import kr.rvs.mclibrary.struct.command.CommandStorage;
import kr.rvs.mclibrary.struct.command.MCCommand;

/**
 * Created by Junhyeong Lim on 2017-08-10.
 */
public class DefaultCommandLayout extends PagingCommandLayout {
    @Override
    public void writeCmdInfoLine(CommandLayoutStorage layoutStorage, CommandStorage storage) {
        StringBuilder builder = layoutStorage.getBuilder();
        MCCommand parent = layoutStorage.getParent();

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
}
