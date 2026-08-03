package me.m56738.easyarmorstands.neoforge.event;

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
import me.m56738.easyarmorstands.neoforge.api.event.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerCommitElementEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerDiscoverElementEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerEditElementEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerElementMenuOpenEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerSessionStartEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerSessionStopEvent;
import me.m56738.easyarmorstands.neoforge.api.event.PlayerSpawnMenuOpenEvent;
import me.m56738.easyarmorstands.platform.entity.Player;
import net.neoforged.neoforge.common.NeoForge;

import static me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity.toNative;
import static me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer.toNative;

public class NeoForgeEventDispatcher implements EventDispatcher {
    @Override
    public boolean dispatchCreateElement(Player player, ElementType type, PropertyContainer properties) {
        PlayerCreateElementEvent event = new PlayerCreateElementEvent(toNative(player), type, properties);
        return !NeoForge.EVENT_BUS.post(event).isCanceled();
    }

    @Override
    public boolean dispatchDestroyElement(Player player, DestroyableElement element) {
        PlayerDestroyElementEvent event = new PlayerDestroyElementEvent(toNative(player), element);
        return !NeoForge.EVENT_BUS.post(event).isCanceled();
    }

    @Override
    public boolean dispatchEditElement(Player player, EditableElement element) {
        PlayerEditElementEvent event = new PlayerEditElementEvent(toNative(player), element);
        return !NeoForge.EVENT_BUS.post(event).isCanceled();
    }

    @Override
    public boolean dispatchDiscoverElement(Player player, EditableElement element) {
        PlayerDiscoverElementEvent event = new PlayerDiscoverElementEvent(toNative(player), element);
        return !NeoForge.EVENT_BUS.post(event).isCanceled();
    }

    @Override
    public <T> boolean dispatchEditProperty(Player player, Element element, Property<T> property, T oldValue, T newValue) {
        PlayerEditPropertyEvent<T> event = new PlayerEditPropertyEvent<>(toNative(player), element, property, oldValue, newValue);
        return !NeoForge.EVENT_BUS.post(event).isCanceled();
    }

    @Override
    public void dispatchCommitElement(Player player, Element element) {
        PlayerCommitElementEvent event = new PlayerCommitElementEvent(toNative(player), element);
        NeoForge.EVENT_BUS.post(event);
    }

    @Override
    public void dispatchSessionStop(Session session) {
        PlayerSessionStopEvent event = new PlayerSessionStopEvent(toNative(session.player()), session);
        NeoForge.EVENT_BUS.post(event);
    }

    @Override
    public void dispatchSessionStart(Session session) {
        PlayerSessionStartEvent event = new PlayerSessionStartEvent(toNative(session.player()), session);
        NeoForge.EVENT_BUS.post(event);
    }

    @Override
    public void dispatchEntityElementInitialize(ConfigurableEntityElement<?> element) {
        EntityElementInitializeEvent event = new EntityElementInitializeEvent(toNative(element.getEntity()), element);
        NeoForge.EVENT_BUS.post(event);
    }

    @Override
    public void dispatchSpawnMenuOpen(Player player, MenuBuilder builder) {
        PlayerSpawnMenuOpenEvent event = new PlayerSpawnMenuOpenEvent(toNative(player), builder);
        NeoForge.EVENT_BUS.post(event);
    }

    @Override
    public void dispatchElementMenuOpen(Player player, Element element, MenuBuilder builder, PropertyContainer properties) {
        PlayerElementMenuOpenEvent event = new PlayerElementMenuOpenEvent(toNative(player), element, builder, properties);
        NeoForge.EVENT_BUS.post(event);
    }
}
