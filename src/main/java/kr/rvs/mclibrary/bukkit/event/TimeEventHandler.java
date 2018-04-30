package kr.rvs.mclibrary.bukkit.event;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by JunHyeong Lim on 2018-04-29
 */
public abstract class TimeEventHandler extends PredicateEventHandler {
    public TimeEventHandler(long tick) {
        super(e -> {
            AtomicInteger counter = new AtomicInteger();
            if (e instanceof TickEvent && counter.getAndIncrement() >= tick) {
                counter.set(0);
                return true;
            }
            return false;
        });
    }
}
