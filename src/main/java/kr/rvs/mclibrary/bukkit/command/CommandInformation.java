package kr.rvs.mclibrary.bukkit.command;

/**
 * Created by Junhyeong Lim on 2017-09-28.
 */
public interface CommandInformation {
    String usage();

    String desc();

    String perm();

    CommandInformation DEFAULT = new CommandInformation() {
        @Override
        public String usage() {
            return "";
        }

        @Override
        public String desc() {
            return "";
        }

        @Override
        public String perm() {
            return "";
        }
    };
}
