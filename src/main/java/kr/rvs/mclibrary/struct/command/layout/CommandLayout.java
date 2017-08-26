package kr.rvs.mclibrary.struct.command.layout;

/**
 * Created by Junhyeong Lim on 2017-08-10.
 */
public interface CommandLayout {
    String PLUGIN_NAME = "%plugin-name%";
    String PERMISSION_NODE = "%perm-node%";

    void writeHelpMessage(CommandLayoutStorage storage);

    default String prefix() {
        return "&c[ " + PLUGIN_NAME + " ] &f";
    }

    default String permissionDeniedMessage() {
        return "&f권한 &e" + PERMISSION_NODE + " &f이(가) 필요합니다.";
    }
}
