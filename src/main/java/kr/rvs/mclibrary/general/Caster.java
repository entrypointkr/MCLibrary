package kr.rvs.mclibrary.general;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by JunHyeong Lim on 2018-04-21
 */
public class Caster {
    private final List<Consumer<Object>> consumers = new ArrayList<>();

    public <T> Caster ifType(Class<T> aClass, Consumer<T> callback) {
        consumers.add(e -> {
            if (aClass.isAssignableFrom(e.getClass())) {
                callback.accept(aClass.cast(e));
            }
        });
        return this;
    }

    public void execute(Object object) {
        consumers.forEach(consumer -> consumer.accept(object));
    }
}
