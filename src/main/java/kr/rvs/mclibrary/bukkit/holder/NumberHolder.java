package kr.rvs.mclibrary.bukkit.holder;

/**
 * Created by Junhyeong Lim on 2017-10-11.
 */
public class NumberHolder {
    private Double number;

    public NumberHolder(Double number) {
        this.number = number;
    }

    public NumberHolder() {
        this(0D);
    }

    public boolean has(double data) {
        return this.number >= data;
    }

    public boolean take(double data) {
        if (has(data)) {
            this.number -= data;
            return true;
        }
        return false;
    }

    public void add(double data) {
        this.number += data;
    }

    public void set(double obj) {
        this.number = obj;
    }

    public Double get() {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
