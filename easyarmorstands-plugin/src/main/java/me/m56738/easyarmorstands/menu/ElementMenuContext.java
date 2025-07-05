package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuContext;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class ElementMenuContext implements MenuContext {
    private final EasPlayer player;
    private final Session session;
    private final Element element;
    private final PropertyContainer properties;
    private final PermissionChecker permissions;
    private final Locale locale;
    private final TagResolver resolver;

    public ElementMenuContext(EasPlayer player, Session session, Element element) {
        this.player = player;
        this.session = session;
        this.element = element;
        this.properties = new TrackedPropertyContainer(element, player);
        this.permissions = player.permissions();
        this.locale = player.locale();
        this.resolver = TagResolver.builder()
                .tag("type", Tag.selfClosingInserting(element.getType().getDisplayName()))
                .build();
    }

    @Override
    public @NotNull Player player() {
        return player.get();
    }

    @Override
    public @Nullable Session session() {
        return session;
    }

    @Override
    public @Nullable Element element() {
        return element;
    }

    @Override
    public @Nullable PropertyContainer properties() {
        return properties;
    }

    @Override
    public @NotNull PropertyContainer properties(@NotNull Element element) {
        return new TrackedPropertyContainer(element, player);
    }

    @Override
    public @NotNull PermissionChecker permissions() {
        return permissions;
    }

    @Override
    public @NotNull Locale locale() {
        return locale;
    }

    @Override
    public @NotNull TagResolver resolver() {
        return resolver;
    }

    @Override
    public @Nullable ColorPickerContext colorPicker() {
        return null;
    }
}
