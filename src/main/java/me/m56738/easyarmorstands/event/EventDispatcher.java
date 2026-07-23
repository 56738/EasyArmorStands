package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.platform.entity.Player;

public interface EventDispatcher {
    boolean dispatchCreateElement(Player player, ElementType type, PropertyContainer properties);

    boolean dispatchDestroyElement(Player player, DestroyableElement element);

    boolean dispatchEditElement(Player player, EditableElement element);

    boolean dispatchDiscoverElement(Player player, EditableElement element);

    <T> boolean dispatchEditProperty(Player player, Element element, Property<T> property, T oldValue, T newValue);

    void dispatchCommitElement(Player player, Element element);

    void dispatchSessionStop(Session session);

    void dispatchSessionStart(Session session);

    void dispatchEntityElementInitialize(ConfigurableEntityElement<?> element);

    void dispatchSpawnMenuOpen(Player player, MenuBuilder builder);

    void dispatchElementMenuOpen(Player player, Element element, MenuBuilder builder, PropertyContainer properties);
}
