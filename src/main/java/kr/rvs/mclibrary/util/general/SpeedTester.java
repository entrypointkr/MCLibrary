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

    public SpeedTester() {
    }

    public SpeedTester(int tryCount) {
        this.tryCount = tryCount;
    }

    public SpeedTester addRunnable(Runnable... runnables) {
        runnableList.addAll(Arrays.asList(runnables));
        return this;
    }

    public void start() {
        int size = runnableList.size();
        for (int i = 0; i < size; i++) {
            long total = 0;
            for (int j = 0; j < tryCount; j++) {
                Runnable runnable = runnableList.get(i);
                long start = System.currentTimeMillis();
                runnable.run();
                total += System.currentTimeMillis() - start;
            }
            System.out.println(i + " 평균: " + total / tryCount);
        }
    }
}
