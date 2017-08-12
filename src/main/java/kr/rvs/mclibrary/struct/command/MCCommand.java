package kr.rvs.mclibrary.struct.command;

import kr.rvs.mclibrary.struct.command.layout.CommandLayout;
import kr.rvs.mclibrary.struct.command.layout.DefaultCommandLayout;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public interface MCCommand {
    Map<String, CommandLayout> CACHED_LAYOUT = new HashMap<>();

    String label();

    default String description() {
        return "A MCLibrary command.";
    }

    default String usage() {
        return "/" + label();
    }

    default String[] aliases() {
        return new String[0];
    }

    default String helpArg() {
        return StringUtils.isAlpha(label()) ? "help" : "도움말";
    }

    default CommandLayout layout() {
        return CACHED_LAYOUT.computeIfAbsent(label(), k -> new DefaultCommandLayout());
    }
}
