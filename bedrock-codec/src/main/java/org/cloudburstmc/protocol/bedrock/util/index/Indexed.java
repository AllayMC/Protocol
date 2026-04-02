package org.cloudburstmc.protocol.bedrock.util.index;

import java.util.List;

public final class Indexed<T> implements Indexable<T> {

    private final List<T> values;
    private final int index;

    public Indexed(List<T> values, int index) {
        this.values = values;
        this.index = index;
    }

    @Override
    public T get() {
        return values.get(index);
    }
}
