package me.m56738.easyarmorstands.util;

import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredIterator<T> implements Iterator<T> {
    private final Iterator<T> iterator;
    private final Predicate<T> predicate;
    private @Nullable T current;
    private boolean valid;

    public FilteredIterator(Iterator<T> iterator, Predicate<T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    private void moveToNext() {
        if (valid) {
            return;
        }
        while (iterator.hasNext()) {
            current = iterator.next();
            if (predicate.test(current)) {
                valid = true;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        moveToNext();
        return valid;
    }

    @Override
    public T next() {
        moveToNext();
        if (!valid) {
            throw new NoSuchElementException();
        }
        T value = current;
        valid = false;
        return value;
    }
}
