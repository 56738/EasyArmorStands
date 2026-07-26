package me.m56738.easyarmorstands.platform.event;

import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventBusImpl implements EventBus {
    @SuppressWarnings({"rawtypes"})
    private final Map<EventType, Entry> subscriptions = new HashMap<>();

    @SuppressWarnings("unchecked")
    private <C> Entry<C> getEntry(EventType<C> type, boolean create) {
        synchronized (subscriptions) {
            if (create) {
                return subscriptions.computeIfAbsent(type, Entry::new);
            } else {
                return subscriptions.get(type);
            }
        }
    }

    @Override
    public <C> void subscribe(EventType<C> type, C callback) {
        getEntry(type, true).subscribe(callback);
    }

    @Override
    public <C> C invoker(EventType<C> type) {
        return getEntry(type, false).invoker();
    }

    private static class Entry<C> {
        private final EventType<C> type;
        private final Set<C> callbacks = new HashSet<>();
        private @Nullable C merged;

        private Entry(EventType<C> type) {
            this.type = type;
        }

        public synchronized void subscribe(C callback) {
            callbacks.add(callback);
            merged = null;
        }

        public synchronized C invoker() {
            if (merged == null) {
                merged = type.dispatcher().merge(callbacks);
            }
            return merged;
        }
    }
}
