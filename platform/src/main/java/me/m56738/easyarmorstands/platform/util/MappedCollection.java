package me.m56738.easyarmorstands.platform.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

public class MappedCollection<T, M> extends AbstractCollection<M> {
    private final Collection<T> collection;
    private final Function<T, M> mapper;

    public MappedCollection(Collection<T> collection, Function<T, M> mapper) {
        this.collection = collection;
        this.mapper = mapper;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public Iterator<M> iterator() {
        return new MappedIterator<>(collection.iterator(), mapper);
    }
}
