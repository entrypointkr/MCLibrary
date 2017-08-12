package kr.rvs.mclibrary.struct.command.layout;

import kr.rvs.mclibrary.struct.command.CommandStorage;
import kr.rvs.mclibrary.struct.command.MCCommand;
import kr.rvs.mclibrary.util.collection.VolatileArrayList;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-08-10.
 */
public interface CommandLayout {
    void writeHelpMessage(StringBuilder builder, MCCommand parent, List<CommandStorage> storages, VolatileArrayList args);
}
