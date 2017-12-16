package kr.rvs.mclibrary.general;


import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-08-17.
 */
public class VarargsParser {
    private final Object[] args;
    private final int count;

    public VarargsParser(int sectionSize, Object... args) {
        this.count = sectionSize;
        this.args = args;
    }

    public VarargsParser(Object[] args) {
        this(2, args);
    }

    public void parse(Consumer<Section> sectionCallback) {
        for (int i = 0; i < args.length / count; i++) {
            int source = i * count;
            Object[] values = new Object[count];
            System.arraycopy(args, source, values, 0, count);
            sectionCallback.accept(new Section(values));
        }
    }

    public class Section {
        private final Object[] values;

        public Section(Object[] values) {
            this.values = values;
        }

        @SuppressWarnings("unchecked")
        public <T> T get(Integer index) {
            Object value = values[index];
            return value != null ? (T) value : null;
        }

        public String getString(Integer index) {
            Object value = values[index];
            return value != null ? String.valueOf(value) : null;
        }

        public <T> Optional<T> getOptional(Integer index) {
            return Optional.ofNullable(get(index));
        }
    }
}
