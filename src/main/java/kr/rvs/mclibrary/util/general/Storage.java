package kr.rvs.mclibrary.util.general;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
public class Storage<T> {
    private T value;

    public Storage(T value) {
        this.value = value;
    }

    public Storage() {
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
