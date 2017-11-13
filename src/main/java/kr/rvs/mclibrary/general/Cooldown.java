package kr.rvs.mclibrary.general;

import java.util.concurrent.TimeUnit;

/**
 * Created by Junhyeong Lim on 2017-09-02.
 */
public class Cooldown {
    private final long coolTime;
    private long lastCount = 0;

    public Cooldown(TimeUnit unit, int number) {
        this.coolTime = unit.toMillis(number);
    }

    public boolean isAfter() {
        return System.currentTimeMillis() >= lastCount;
    }

    public void cooldown() {
        lastCount = System.currentTimeMillis() + coolTime;
    }

    public boolean count() {
        if (isAfter()) {
            cooldown();
            return true;
        }
        return false;
    }

    public long remainCooltime(TimeUnit unit) {
        long currTime = System.currentTimeMillis();
        return unit.convert(lastCount > currTime ? lastCount - currTime : 0, TimeUnit.MILLISECONDS);
    }

    public long remainCooltime() {
        return remainCooltime(TimeUnit.SECONDS);
    }

    public long cooltime(TimeUnit unit) {
        return unit.convert(coolTime, TimeUnit.MILLISECONDS);
    }

    public long cooltime() {
        return cooltime(TimeUnit.SECONDS);
    }
}
