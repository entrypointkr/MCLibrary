package kr.rvs.mclibrary.command;

import java.util.List;

/**
 * Created by JunHyeong Lim on 2018-05-15
 */
public class EmptyCommand<T> implements ICommand<T> {
    @Override
    public void execute(T data, List<String> args) {
        // Ignore
    }
}
