package kr.rvs.mclibrary.bukkit.holder;

/**
 * Created by Junhyeong Lim on 2017-10-11.
 */
public class NumberHolder {
    private Integer data;

    public NumberHolder(Integer data) {
        this.data = data;
    }

    public NumberHolder() {
        this(0);
    }

    public boolean has(int data) {
        return this.data >= data;
    }

    public boolean take(int data) {
        if (has(data)) {
            this.data -= data;
            return true;
        }
        return false;
    }

    public void add(int data) {
        this.data += data;
    }

    public void set(Integer obj) {
        this.data = obj;
    }

    public Integer get() {
        return data;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }
}
