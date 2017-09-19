package kr.rvs.mclibrary.bukkit.command;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public interface SubCommand extends CommandExecutable {
    String args();

    default int min() {
        return 0;
    }

    default int max() {
        return Integer.MAX_VALUE;
    }
}
