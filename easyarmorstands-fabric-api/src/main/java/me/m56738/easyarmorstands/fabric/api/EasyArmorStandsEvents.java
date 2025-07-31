package me.m56738.easyarmorstands.fabric.api;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class EasyArmorStandsEvents {
    private EasyArmorStandsEvents() {
    }

    public static final Event<SessionStarted> SESSION_STARTED = EventFactory.createArrayBacked(SessionStarted.class, callbacks -> session -> {
        for (SessionStarted callback : callbacks) {
            callback.onSessionStarted(session);
        }
    });

    public static final Event<SessionStopped> SESSION_STOPPED = EventFactory.createArrayBacked(SessionStopped.class, callbacks -> session -> {
        for (SessionStopped callback : callbacks) {
            callback.onSessionStopped(session);
        }
    });

    public static final Event<InitializeDefaultElement> INITIALIZE_DEFAULT_ELEMENT = EventFactory.createArrayBacked(InitializeDefaultElement.class, callbacks -> session -> {
        for (InitializeDefaultElement callback : callbacks) {
            callback.onInitializeDefaultElement(session);
        }
    });

    public static final Event<DiscoverElement> DISCOVER_ELEMENT = EventFactory.createArrayBacked(DiscoverElement.class, callbacks -> (player, element) -> {
        for (DiscoverElement callback : callbacks) {
            if (!callback.onDiscoverElement(player, element)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<SelectElement> SELECT_ELEMENT = EventFactory.createArrayBacked(SelectElement.class, callbacks -> (player, element) -> {
        for (SelectElement callback : callbacks) {
            if (!callback.onSelectElement(player, element)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<CreateElement> CREATE_ELEMENT = EventFactory.createArrayBacked(CreateElement.class, callbacks -> (player, type, properties) -> {
        for (CreateElement callback : callbacks) {
            if (!callback.onCreateElement(player, type, properties)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<DestroyElement> DESTROY_ELEMENT = EventFactory.createArrayBacked(DestroyElement.class, callbacks -> (player, element) -> {
        for (DestroyElement callback : callbacks) {
            if (!callback.onDestroyElement(player, element)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<ChangeProperty> CHANGE_PROPERTY = EventFactory.createArrayBacked(ChangeProperty.class, callbacks -> (player, element, change) -> {
        for (ChangeProperty callback : callbacks) {
            if (!callback.onChangeProperty(player, element, change)) {
                return false;
            }
        }
        return true;
    });

    @FunctionalInterface
    public interface SessionStarted {
        void onSessionStarted(Session session);
    }

    @FunctionalInterface
    public interface SessionStopped {
        void onSessionStopped(Session session);
    }

    @FunctionalInterface
    public interface InitializeDefaultElement {
        void onInitializeDefaultElement(DefaultEntityElement element);
    }

    @FunctionalInterface
    public interface DiscoverElement {
        boolean onDiscoverElement(Player player, EditableElement element);
    }

    @FunctionalInterface
    public interface SelectElement {
        boolean onSelectElement(Player player, EditableElement element);
    }

    @FunctionalInterface
    public interface CreateElement {
        boolean onCreateElement(Player player, ElementType type, PropertyContainer properties);
    }

    @FunctionalInterface
    public interface DestroyElement {
        boolean onDestroyElement(Player player, DestroyableElement element);
    }

    @FunctionalInterface
    public interface ChangeProperty {
        boolean onChangeProperty(Player player, Element element, PropertyChange<?> change);
    }

    public record PropertyChange<T>(Property<T> type, T value) {
    }
}
