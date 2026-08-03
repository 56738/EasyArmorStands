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
import me.m56738.easyarmorstands.fabric.api.event.EasyArmorStandsEvents;
import me.m56738.easyarmorstands.platform.entity.Player;
import org.jspecify.annotations.NullMarked;

import static me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity.toNative;
import static me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer.toNative;

@NullMarked
public class FabricEventDispatcher implements EventDispatcher {
    @Override
    public boolean dispatchCreateElement(Player player, ElementType type, PropertyContainer properties) {
        return EasyArmorStandsEvents.CREATE_ELEMENT.invoker().onCreateElement(toNative(player), type, properties);
    }

    @Override
    public boolean dispatchDestroyElement(Player player, DestroyableElement element) {
        return EasyArmorStandsEvents.DESTROY_ELEMENT.invoker().onDestroyElement(toNative(player), element);
    }

    @Override
    public boolean dispatchEditElement(Player player, EditableElement element) {
        return EasyArmorStandsEvents.EDIT_ELEMENT.invoker().onEditElement(toNative(player), element);
    }

    @Override
    public boolean dispatchDiscoverElement(Player player, EditableElement element) {
        return EasyArmorStandsEvents.DISCOVER_ELEMENT.invoker().onDiscoverElement(toNative(player), element);
    }

    @Override
    public <T> boolean dispatchEditProperty(Player player, Element element, Property<T> property, T oldValue, T newValue) {
        return EasyArmorStandsEvents.EDIT_PROPERTY.invoker().onEditProperty(toNative(player), element, property, oldValue, newValue);
    }

    @Override
    public void dispatchCommitElement(Player player, Element element) {
        EasyArmorStandsEvents.COMMIT_ELEMENT.invoker().onCommitElement(toNative(player), element);
    }

    @Override
    public void dispatchSessionStop(Session session) {
        EasyArmorStandsEvents.SESSION_STOP.invoker().onSessionStop(toNative(session.player()), session);
    }

    @Override
    public void dispatchSessionStart(Session session) {
        EasyArmorStandsEvents.SESSION_START.invoker().onSessionStart(toNative(session.player()), session);
    }

    @Override
    public void dispatchEntityElementInitialize(ConfigurableEntityElement<?> element) {
        EasyArmorStandsEvents.ENTITY_ELEMENT_INITIALIZE.invoker().onEntityElementInitialize(toNative(element.getEntity()), element);
    }

    @Override
    public void dispatchSpawnMenuOpen(Player player, MenuBuilder builder) {
        EasyArmorStandsEvents.SPAWN_MENU_OPEN.invoker().onSpawnMenuOpen(toNative(player), builder);
    }

    @Override
    public void dispatchElementMenuOpen(Player player, Element element, MenuBuilder builder, PropertyContainer properties) {
        EasyArmorStandsEvents.ELEMENT_MENU_OPEN.invoker().onElementMenuOpen(toNative(player), element, builder, properties);
    }
}
