package kr.rvs.mclibrary.general;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-01-30.
 */
public class SpeedTester {
    private final Map<String, Runnable> runnableMap = new LinkedHashMap<>();
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
        for (int i = 0; i < runnables.length; i++) {
            addRunnable(String.valueOf(i), runnables[i]);
        }
        return this;
    }

    public SpeedTester addRunnable(String name, Runnable runnable) {
        runnableMap.put(name, runnable);
        return this;
    }

    public void start() {
        // Preheat
        runnableMap.values().forEach(Runnable::run);

        // Code
        for (Map.Entry<String, Runnable> entry : runnableMap.entrySet()) {
            long total = 0;
            for (int i = 0; i < tryCount; i++) {
                long start = getTime();
                entry.getValue().run();
                total += getTime() - start;
            }
            System.out.println(entry.getKey() + " 평균: " + total / tryCount);
        }
    }

    private long getTime() {
        return nanoTime ? System.nanoTime() : System.currentTimeMillis();
    }
}
