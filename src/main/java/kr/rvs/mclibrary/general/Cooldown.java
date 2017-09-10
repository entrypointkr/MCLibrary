package kr.rvs.mclibrary.general;

import java.util.concurrent.TimeUnit;

/**
 * Created by Junhyeong Lim on 2017-09-02.
 */
public class Cooldown {
    private final TimeUnit unit;
    private final int number;
    private long lastCount = 0;

    public Cooldown(TimeUnit unit, int number) {
        this.unit = unit;
        this.number = number;
    }

    public boolean isAfter() {
        return System.currentTimeMillis() >= lastCount;
    }

    public void cooldown() {
        lastCount = System.currentTimeMillis() + unit.toMillis(number);
    }

    public boolean count() {
        if (isAfter()) {
            cooldown();
            return true;
        }
        return false;
    }

    public long cooltime() {
        long currTime = System.currentTimeMillis();
        return lastCount > currTime ? lastCount - currTime : 0;
    }
}
