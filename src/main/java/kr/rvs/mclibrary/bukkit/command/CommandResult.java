package kr.rvs.mclibrary.bukkit.command;

/**
 * Created by Junhyeong Lim on 2017-08-26.
 */
public class CommandResult {
    private final State state;
    private final CommandStorage storage;

    public CommandResult(State state, CommandStorage storage) {
        this.state = state;
        this.storage = storage;
    }

    public State getState() {
        return state;
    }

    public CommandStorage getStorage() {
        return storage;
    }

    public enum State {
        DONE,
        PERMISSION_DENIED,
        HELP
    }
}
