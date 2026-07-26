package me.m56738.easyarmorstands.platform.event;

public interface EventBus {
    static EventBus of() {
        return new EventBusImpl();
    }

    <C> void subscribe(EventType<C> type, C callback);

    <C> C invoker(EventType<C> type);
}
