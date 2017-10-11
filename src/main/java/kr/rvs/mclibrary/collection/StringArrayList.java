package kr.rvs.mclibrary.collection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class StringArrayList extends ArrayList<String> {
    public StringArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public StringArrayList() {
    }

    public StringArrayList(Collection<? extends String> c) {
        super(c);
    }

    private int addWithLineBreak(int index, String element) {
        for (String content : element.split("\n")) {
            super.add(index++, content);
        }
        return index;
    }

    @Override
    public boolean add(String s) {
        add(size(), s);
        return true;
    }

    @Override
    public void add(int index, String element) {
        addWithLineBreak(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        return addAll(size(), c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        for (String content : c) {
            index = addWithLineBreak(index, content);
        }
        return true;
    }
}
