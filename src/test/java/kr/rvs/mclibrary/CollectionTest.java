package kr.rvs.mclibrary;

import kr.rvs.mclibrary.collection.StringArrayList;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class CollectionTest extends Assert {
    @Test
    public void stringArrayList() {
        StringArrayList list = new StringArrayList();
        String test = "The\nTest\nString";
        list.add(test);
        list.addAll(Arrays.asList(test, test));
        assertEquals(list, Arrays.asList(
                "The",
                "Test",
                "String",
                "The",
                "Test",
                "String",
                "The",
                "Test",
                "String"
        ));
    }
}
