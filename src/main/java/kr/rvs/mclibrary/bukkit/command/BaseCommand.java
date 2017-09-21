package kr.rvs.mclibrary.bukkit.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-09-19.
 */
public abstract class BaseCommand {
    private String label;

    public abstract String label();

    public String getLabel() {
        if (label == null)
            label = label().replace("/", "").trim();
        return label;
    }

    public String description() {
        return "MCLibrary command";
    }

    public String usage() {
        return '/' + getLabel();
    }

    public List<String> aliases() {
        return new ArrayList<>();
    }

    public abstract SubCommand[] commands();

    public boolean helpCommand() {
        return true;
    }
}
