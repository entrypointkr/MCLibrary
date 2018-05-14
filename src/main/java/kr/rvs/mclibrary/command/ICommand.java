package kr.rvs.mclibrary.command;

import java.util.List;

/**
 * Created by JunHyeong Lim on 2018-05-15
 */
public interface ICommand<T> {
    void execute(T data, List<String> args);
}
