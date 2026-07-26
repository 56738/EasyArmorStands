package me.m56738.easyarmorstands.platform.event;

@FunctionalInterface
public interface EventDispatcher<C> {
    C merge(Iterable<C> callbacks);
}
