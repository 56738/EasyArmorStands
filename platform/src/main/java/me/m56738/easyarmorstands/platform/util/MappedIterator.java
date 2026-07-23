package me.m56738.easyarmorstands.platform.util;

import java.util.Iterator;
import java.util.function.Function;

public class MappedIterator<T, M> implements Iterator<M> {
    private final Iterator<T> iterator;
    private final Function<T, M> mapper;

    public MappedIterator(Iterator<T> iterator, Function<T, M> mapper) {
        this.iterator = iterator;
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public M next() {
        return mapper.apply(iterator.next());
    }
}
