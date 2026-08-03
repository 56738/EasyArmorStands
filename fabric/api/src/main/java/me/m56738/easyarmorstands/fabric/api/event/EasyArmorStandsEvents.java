package me.m56738.easyarmorstands.fabric.api.event;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public final class EasyArmorStandsEvents {
    private EasyArmorStandsEvents() {
    }

    public static final Event<CreateElement> CREATE_ELEMENT = EventFactory.createArrayBacked(CreateElement.class, callbacks ->
            (player, type, properties) -> {
                for (CreateElement callback : callbacks) {
                    if (!callback.onCreateElement(player, type, properties)) {
                        return false;
                    }
                }
                return true;
            });

    public static final Event<DestroyElement> DESTROY_ELEMENT = EventFactory.createArrayBacked(DestroyElement.class, callbacks ->
            (player, element) -> {
                for (DestroyElement callback : callbacks) {
                    if (!callback.onDestroyElement(player, element)) {
                        return false;
                    }
                }
                return true;
            });

    public static final Event<EditElement> EDIT_ELEMENT = EventFactory.createArrayBacked(EditElement.class, callbacks ->
            (player, element) -> {
                for (EditElement callback : callbacks) {
                    if (!callback.onEditElement(player, element)) {
                        return false;
                    }
                }
                return true;
            });

    public static final Event<DiscoverElement> DISCOVER_ELEMENT = EventFactory.createArrayBacked(DiscoverElement.class, callbacks ->
            (player, element) -> {
                for (DiscoverElement callback : callbacks) {
                    if (!callback.onDiscoverElement(player, element)) {
                        return false;
                    }
                }
                return true;
            });

    public static final Event<EditProperty> EDIT_PROPERTY = EventFactory.createArrayBacked(EditProperty.class, callbacks ->
            new EditProperty() {
                @Override
                public <T> boolean onEditProperty(ServerPlayer player, Element element, Property<T> property, T oldValue, T newValue) {
                    for (EditProperty callback : callbacks) {
                        if (!callback.onEditProperty(player, element, property, oldValue, newValue)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
    );

    public static final Event<CommitElement> COMMIT_ELEMENT = EventFactory.createArrayBacked(CommitElement.class, callbacks ->
            (player, element) -> {
                for (CommitElement callback : callbacks) {
                    callback.onCommitElement(player, element);
                }
            });

    public static final Event<SessionStop> SESSION_STOP = EventFactory.createArrayBacked(SessionStop.class, callbacks ->
            (player, session) -> {
                for (SessionStop callback : callbacks) {
                    callback.onSessionStop(player, session);
                }
            });

    public static final Event<SessionStart> SESSION_START = EventFactory.createArrayBacked(SessionStart.class, callbacks ->
            (player, session) -> {
                for (SessionStart callback : callbacks) {
                    callback.onSessionStart(player, session);
                }
            });

    public static final Event<EntityElementInitialize> ENTITY_ELEMENT_INITIALIZE = EventFactory.createArrayBacked(EntityElementInitialize.class, callbacks ->
            (entity, element) -> {
                for (EntityElementInitialize callback : callbacks) {
                    callback.onEntityElementInitialize(entity, element);
                }
            });

    public static final Event<SpawnMenuOpen> SPAWN_MENU_OPEN = EventFactory.createArrayBacked(SpawnMenuOpen.class, callbacks ->
            (player, menuBuilder) -> {
                for (SpawnMenuOpen callback : callbacks) {
                    callback.onSpawnMenuOpen(player, menuBuilder);
                }
            });

    public static final Event<ElementMenuOpen> ELEMENT_MENU_OPEN = EventFactory.createArrayBacked(ElementMenuOpen.class, callbacks ->
            (player, element, menuBuilder, properties) -> {
                for (ElementMenuOpen callback : callbacks) {
                    callback.onElementMenuOpen(player, element, menuBuilder, properties);
                }
            });

    public interface CreateElement {
        boolean onCreateElement(ServerPlayer player, ElementType type, PropertyContainer properties);
    }

    public interface DestroyElement {
        boolean onDestroyElement(ServerPlayer player, DestroyableElement element);
    }

    public interface EditElement {
        boolean onEditElement(ServerPlayer player, EditableElement element);
    }

    public interface DiscoverElement {
        boolean onDiscoverElement(ServerPlayer player, EditableElement element);
    }

    public interface EditProperty {
        <T> boolean onEditProperty(ServerPlayer player, Element element, Property<T> property, T oldValue, T newValue);
    }

    public interface CommitElement {
        void onCommitElement(ServerPlayer player, Element element);
    }

    public interface SessionStop {
        void onSessionStop(ServerPlayer player, Session session);
    }

    public interface SessionStart {
        void onSessionStart(ServerPlayer player, Session session);
    }

    public interface EntityElementInitialize {
        void onEntityElementInitialize(Entity entity, ConfigurableEntityElement<?> element);
    }

    public interface SpawnMenuOpen {
        void onSpawnMenuOpen(ServerPlayer player, MenuBuilder menuBuilder);
    }

    public interface ElementMenuOpen {
        void onElementMenuOpen(ServerPlayer player, Element element, MenuBuilder menuBuilder, PropertyContainer properties);
    }
}
