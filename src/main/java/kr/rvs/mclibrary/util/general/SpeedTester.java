package kr.rvs.mclibrary.util.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-01-30.
 */
public class SpeedTester {
    private final List<Runnable> runnableList = new ArrayList<>();
    private int tryCount = 10;
    private final boolean nanoTime;

    public SpeedTester() {
        this(10, true);
    }

    public SpeedTester(int tryCount, boolean nanoTime) {
        this.tryCount = tryCount;
        this.nanoTime = nanoTime;
    }

    public SpeedTester addRunnable(Runnable... runnables) {
        runnableList.addAll(Arrays.asList(runnables));
        return this;
    }

    public void start() {
        // Preheat
        runnableList.forEach(Runnable::run);

        // Code
        int size = runnableList.size();
        for (int i = 0; i < size; i++) {
            long total = 0;
            for (int j = 0; j < tryCount; j++) {
                Runnable runnable = runnableList.get(i);
                long start = getTime();
                runnable.run();
                total += getTime() - start;
            }
            System.out.println(i + " 평균: " + total / tryCount);
        }
    }

    private long getTime() {
        return nanoTime ? System.nanoTime() : System.currentTimeMillis();
    }
}
