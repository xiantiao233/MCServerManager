package fun.xiantiao.mcservermanage.utils;

import java.util.HashSet;
import java.util.Set;

public class MissingCheck<T> {
    public static final String[] coreCommonVersions = {
            "1.7.10","1.8.8","1.12.2","1.13.2","1.14.4","1.15.2","1.16.5","1.17.1","1.18.2","1.19.4","1.20.4","1.20.6","1.21.1"
    };

    private final Set<T> first;
    private final Set<T> add;

    public MissingCheck(Set<T> first) {
        this.first = first;
        this.add = new HashSet<>();
    }
    public MissingCheck(T[] first) {
        this.first = new HashSet<>();
        for (T t : first) {
            if (t != null) this.first.add(t);
        }
        this.add = new HashSet<>();
    }

    public void add(T t) {
        add.add(t);
    }

    public Set<T> getMissing() {
        Set<T> missing = new HashSet<>(first);
        missing.removeAll(add);
        return missing;
    }

    public String toString() {
        return getMissing().toString();
    }
}
