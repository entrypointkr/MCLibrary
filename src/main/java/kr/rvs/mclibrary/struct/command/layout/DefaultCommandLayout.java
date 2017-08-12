package kr.rvs.mclibrary.struct.command.layout;

import kr.rvs.mclibrary.struct.command.CommandStorage;
import kr.rvs.mclibrary.struct.command.MCCommand;

/**
 * Created by Junhyeong Lim on 2017-08-10.
 */
public class DefaultCommandLayout extends PagingCommandLayout {
    @Override
    public void writeCmdInfoLine(CommandLayoutStorage layoutStorage, CommandStorage storage) {
        MCCommand parent = layoutStorage.getParent();

        layoutStorage.append("&6/").append(parent.label());
        if (storage.hasArgs())
            layoutStorage.append(" ").append(storage.getArgs());
        if (storage.hasUsage())
            layoutStorage.append(" ").append(storage.getUsage());
        layoutStorage.append(": &f");
        if (storage.hasDescription())
            layoutStorage.append(storage.getDescription());
        else
            layoutStorage.append("'/").append(parent.label()).append("' 의 하위 명령어");
    }
}
