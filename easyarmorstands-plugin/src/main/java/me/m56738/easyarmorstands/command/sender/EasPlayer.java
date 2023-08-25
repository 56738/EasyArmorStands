package me.m56738.easyarmorstands.command.sender;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.event.player.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.session.SessionImpl;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class EasPlayer extends EasCommandSender implements ChangeContext {
    private final @NotNull Player player;
    private final @NotNull History history;

    public EasPlayer(@NotNull Player player, @NotNull Audience audience) {
        super(player, audience);
        this.player = player;
        this.history = EasyArmorStands.getInstance().getHistory(player);
    }

    public EasPlayer(@NotNull Player player) {
        this(player, EasyArmorStands.getInstance().getAdventure().player(player));
    }

    @Override
    public @NotNull Player get() {
        return player;
    }

    @Override
    public @NotNull History history() {
        return history;
    }

    @Override
    public @NotNull Locale locale() {
        return getOrDefault(Identity.LOCALE, Locale.US);
    }

    public @Nullable SessionImpl session() {
        return EasyArmorStands.getInstance().getSessionManager().getSession(player);
    }

    @Override
    public boolean canCreateElement(ElementType type, PropertyContainer properties) {
        PlayerCreateElementEvent event = new PlayerCreateElementEvent(player, type, properties);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean canDestroyElement(DestroyableElement element) {
        PlayerDestroyElementEvent event = new PlayerDestroyElementEvent(player, element);
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
