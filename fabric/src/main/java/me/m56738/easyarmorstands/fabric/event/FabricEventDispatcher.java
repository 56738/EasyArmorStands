package me.m56738.easyarmorstands.fabric.event;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.event.EventDispatcher;
import me.m56738.easyarmorstands.platform.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class FabricEventDispatcher implements EventDispatcher {
    @Override
    public boolean dispatchCreateElement(Player player, ElementType type, PropertyContainer properties) {
        return true;
    }

    @Override
    public boolean dispatchDestroyElement(Player player, DestroyableElement element) {
        return true;
    }

    @Override
    public boolean dispatchEditElement(Player player, EditableElement element) {
        return true;
    }

    @Override
    public boolean dispatchDiscoverElement(Player player, EditableElement element) {
        return true;
    }

    @Override
    public <T> boolean dispatchEditProperty(Player player, Element element, Property<T> property, T oldValue, T newValue) {
        return true;
    }

    @Override
    public void dispatchCommitElement(Player player, Element element) {

    }

    @Override
    public void dispatchSessionStop(Session session) {

    }

    @Override
    public void dispatchSessionStart(Session session) {

    }

    @Override
    public void dispatchEntityElementInitialize(ConfigurableEntityElement<?> element) {

    }

    @Override
    public void dispatchSpawnMenuOpen(Player player, MenuBuilder builder) {

    }

    @Override
    public void dispatchElementMenuOpen(Player player, Element element, MenuBuilder builder, PropertyContainer properties) {

    }
}
