package me.m56738.easyarmorstands.paper.event;

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
import me.m56738.easyarmorstands.paper.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.paper.api.event.menu.ElementMenuOpenEvent;
import me.m56738.easyarmorstands.paper.api.event.menu.SpawnMenuOpenEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerCommitElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerDiscoverElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerEditElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.paper.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.paper.api.event.session.SessionStopEvent;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import org.bukkit.Bukkit;

public class PaperEventDispatcher implements EventDispatcher {
    @Override
    public boolean dispatchCreateElement(Player player, ElementType type, PropertyContainer properties) {
        PlayerCreateElementEvent event = new PlayerCreateElementEvent(PaperPlayer.toNative(player), type, properties);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean dispatchDestroyElement(Player player, DestroyableElement element) {
        PlayerDestroyElementEvent event = new PlayerDestroyElementEvent(PaperPlayer.toNative(player), element);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean dispatchEditElement(Player player, EditableElement element) {
        PlayerEditElementEvent event = new PlayerEditElementEvent(PaperPlayer.toNative(player), element);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean dispatchDiscoverElement(Player player, EditableElement element) {
        PlayerDiscoverElementEvent event = new PlayerDiscoverElementEvent(PaperPlayer.toNative(player), element);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public <T> boolean dispatchEditProperty(Player player, Element element, Property<T> property, T oldValue, T newValue) {
        PlayerEditPropertyEvent<T> event = new PlayerEditPropertyEvent<>(PaperPlayer.toNative(player), element, property, oldValue, newValue);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public void dispatchCommitElement(Player player, Element element) {
        PlayerCommitElementEvent event = new PlayerCommitElementEvent(PaperPlayer.toNative(player), element);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void dispatchSessionStop(Session session) {
        SessionStopEvent event = new SessionStopEvent(session);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void dispatchSessionStart(Session session) {
        SessionStartEvent event = new SessionStartEvent(session);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void dispatchEntityElementInitialize(ConfigurableEntityElement<?> element) {
        EntityElementInitializeEvent event = new EntityElementInitializeEvent(element);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void dispatchSpawnMenuOpen(Player player, MenuBuilder builder) {
        SpawnMenuOpenEvent event = new SpawnMenuOpenEvent(PaperPlayer.toNative(player), builder);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void dispatchElementMenuOpen(Player player, Element element, MenuBuilder builder, PropertyContainer properties) {
        ElementMenuOpenEvent event = new ElementMenuOpenEvent(PaperPlayer.toNative(player), element, builder, properties);
        Bukkit.getPluginManager().callEvent(event);
    }
}
