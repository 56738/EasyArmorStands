package me.m56738.easyarmorstands.api.context;

import java.io.Closeable;

public interface ManagedChangeContext extends ChangeContext, Closeable {
    @Override
    default void close() {
        commit();
    }
}
