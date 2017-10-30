package kr.rvs.mclibrary;

import kr.rvs.mclibrary.general.VarargsParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-10-30.
 */
public class GeneralTest extends Assert {
    @Test
    public void varargsParser() {
        String[] args = new String[] {
                "a",
                "b",
                "c",
                "d"
        };
        List<String> argList = new ArrayList<>(4);
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section -> argList.addAll(Arrays.asList(section.get(0), section.get(1))));
        assertArrayEquals(args, argList.toArray());
    }
}
