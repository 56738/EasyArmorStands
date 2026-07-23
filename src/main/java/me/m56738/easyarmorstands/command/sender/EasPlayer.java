package me.m56738.easyarmorstands.command.sender;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.event.EventDispatcher;
import me.m56738.easyarmorstands.history.ChangeTracker;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.session.SessionImpl;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class EasPlayer extends EasCommandSender {
    private final @NotNull EasyArmorStandsCommon eas;
    private final @NotNull Player player;
    private @Nullable History history;
    private @Nullable Clipboard clipboard;

    public EasPlayer(@NotNull EasyArmorStandsCommon eas, @NotNull Player player) {
        super(player);
        this.eas = eas;
        this.player = player;
    }

    @Override
    public @NotNull Player get() {
        return player;
    }

    public @NotNull History history() {
        if (history == null) {
            eas.platform().checkMainThread();
            history = eas.getHistory(player);
        }
        return history;
    }

    public @NotNull Clipboard clipboard() {
        if (clipboard == null) {
            eas.platform().checkMainThread();
            clipboard = eas.getClipboard(player);
        }
        return clipboard;
    }

    public @NotNull ChangeTracker tracker() {
        return history().getTracker();
    }

    public @NotNull Locale locale() {
        return getOrDefault(Identity.LOCALE, Locale.US);
    }

    public @Nullable SessionImpl session() {
        return eas.sessionManager().getSession(player);
    }

    private EventDispatcher getEventDispatcher() {
        return eas.eventDispatcher();
    }

    public boolean canCreateElement(ElementType type, PropertyContainer properties) {
        if (!type.canSpawn(player)) {
            return false;
        }
        return getEventDispatcher().dispatchCreateElement(player, type, properties);
    }

    public boolean canDestroyElement(DestroyableElement element) {
        if (!element.canDestroy(player)) {
            return false;
        }
        return getEventDispatcher().dispatchDestroyElement(player, element);
    }

    public boolean canEditElement(EditableElement element) {
        if (!element.canEdit(player)) {
            return false;
        }
        return getEventDispatcher().dispatchEditElement(player, element);
    }

    public boolean canDiscoverElement(EditableElement element) {
        if (!element.canEdit(player)) {
            return false;
        }
        return getEventDispatcher().dispatchDiscoverElement(player, element);
    }

    public <T> boolean canChangeProperty(Element element, Property<T> property, T value) {
        if (!element.isValid()) {
            return false;
        }
        return getEventDispatcher().dispatchEditProperty(player, element, property, property.getValue(), value);
    }
}
