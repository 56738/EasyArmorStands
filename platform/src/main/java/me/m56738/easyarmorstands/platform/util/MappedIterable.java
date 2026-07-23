package me.m56738.easyarmorstands.platform.util;

import java.util.Iterator;
import java.util.function.Function;

public class MappedIterable<T, M> implements Iterable<M> {
    private final Iterable<T> iterable;
    private final Function<T, M> mapper;

    public MappedIterable(Iterable<T> iterable, Function<T, M> mapper) {
        this.iterable = iterable;
        this.mapper = mapper;
    }

    @Override
    public Iterator<M> iterator() {
        return new MappedIterator<>(iterable.iterator(), mapper);
    }
}
