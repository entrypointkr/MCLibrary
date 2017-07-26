package kr.rvs.mclibrary.struct.command;

/**
 * Created by Junhyeong Lim on 2017-07-26.
 */
public interface MCCommand {
    String label();

    default String description() {
        return "";
    }

    default String usage() {
        return "/";
    }

    default String[] aliases() {
        return new String[0];
    }
}
