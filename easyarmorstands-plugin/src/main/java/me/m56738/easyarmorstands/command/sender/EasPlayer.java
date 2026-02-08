package me.m56738.easyarmorstands.command.sender;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.event.player.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerDiscoverElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerEditElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.history.ChangeTracker;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.lib.kyori.adventure.audience.Audience;
import me.m56738.easyarmorstands.lib.kyori.adventure.identity.Identity;
import me.m56738.easyarmorstands.session.SessionImpl;
import me.m56738.easyarmorstands.util.MainThreadExecutor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class EasPlayer extends EasCommandSender implements ChangeContext {
    private final @NotNull Player player;
    private @Nullable History history;
    private @Nullable Clipboard clipboard;

    public EasPlayer(@NotNull Player player, @NotNull Audience audience) {
        super(player, audience);
        this.player = player;
    }

    public EasPlayer(@NotNull Player player) {
        this(player, EasyArmorStandsPlugin.getInstance().getAdventure().player(player));
    }

    @Override
    public @NotNull Player get() {
        return player;
    }

    @Override
    public @NotNull History history() {
        if (history == null) {
            MainThreadExecutor.checkMainThread();
            history = EasyArmorStandsPlugin.getInstance().getHistory(player);
        }
        return history;
    }

    public @NotNull Clipboard clipboard() {
        if (clipboard == null) {
            MainThreadExecutor.checkMainThread();
            clipboard = EasyArmorStandsPlugin.getInstance().getClipboard(player);
        }
        return clipboard;
    }

    @Override
    public @NotNull ChangeTracker tracker() {
        return history().getTracker();
    }

    @Override
    public @NotNull Locale locale() {
        return getOrDefault(Identity.LOCALE, Locale.US);
    }

    @Override
    public @NotNull Player player() {
        return player;
    }

    public @Nullable SessionImpl session() {
        return EasyArmorStandsPlugin.getInstance().sessionManager().getSession(player);
    }

    @Override
    public boolean canCreateElement(ElementType type, PropertyContainer properties) {
        if (!type.canSpawn(player)) {
            return false;
        }
        PlayerCreateElementEvent event = new PlayerCreateElementEvent(player, type, properties);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean canDestroyElement(DestroyableElement element) {
        if (!element.canDestroy(player)) {
            return false;
        }
        PlayerDestroyElementEvent event = new PlayerDestroyElementEvent(player, element);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean canEditElement(EditableElement element) {
        if (!element.canEdit(player)) {
            return false;
        }
        PlayerEditElementEvent event = new PlayerEditElementEvent(player, element);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean canDiscoverElement(EditableElement element) {
        if (!element.canEdit(player)) {
            return false;
        }
        PlayerDiscoverElementEvent event = new PlayerDiscoverElementEvent(player, element);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public <T> boolean canChangeProperty(Element element, Property<T> property, T value) {
        PlayerEditPropertyEvent<T> event = new PlayerEditPropertyEvent<>(player, element, property, property.getValue(), value);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
