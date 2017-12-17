package kr.rvs.mclibrary.general;

import kr.rvs.mclibrary.Static;
import org.apache.commons.lang.Validate;

/**
 * Created by Junhyeong Lim on 2017-12-18.
 */
public class Timings {
    private final String name;
    private long startTime = -1;

    public Timings(String name) {
        this.name = name;
    }

    public Timings start() {
        this.startTime = System.currentTimeMillis();
        return this;
    }

    private long timeDiff() {
        return System.currentTimeMillis() - startTime;
    }

    public void stop() {
        Validate.isTrue(startTime >= 0);
        long diff = timeDiff();
        Static.log(String.format("The %s elapsed %s ms, %s seconds.",
                name, diff, diff / 60));
    }
}
