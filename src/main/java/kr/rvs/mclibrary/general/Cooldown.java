package kr.rvs.mclibrary.general;

import java.util.concurrent.TimeUnit;

/**
 * Created by Junhyeong Lim on 2017-09-02.
 */
public class Cooldown {
    private final long cooltime;
    private long lastCount = 0;

    public Cooldown(TimeUnit unit, int number) {
        this.cooltime = unit.toMillis(number);
    }

    public boolean isAfter() {
        return System.currentTimeMillis() >= lastCount;
    }

    public void cooldown() {
        lastCount = System.currentTimeMillis() + cooltime;
    }

    public boolean count() {
        if (isAfter()) {
            cooldown();
            return true;
        }
        return false;
    }

    public long cooltime(TimeUnit unit) {
        long currTime = System.currentTimeMillis();
        return unit.convert(lastCount > currTime ? lastCount - currTime : 0, TimeUnit.MILLISECONDS);
    }
}
