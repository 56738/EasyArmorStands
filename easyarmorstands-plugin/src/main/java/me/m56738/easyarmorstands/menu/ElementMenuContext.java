package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class ElementMenuContext implements MenuContext {
    private final Player player;
    private final Session session;
    private final Element element;
    private final Locale locale;
    private final TagResolver resolver;

    public ElementMenuContext(Player player, Session session, Element element) {
        this.player = player;
        this.session = session;
        this.element = element;
        this.locale = player.getOrDefault(Identity.LOCALE, Locale.US);
        this.resolver = TagResolver.builder()
                .tag("type", Tag.selfClosingInserting(element.getType().getDisplayName()))
                .build();
    }

    @Override
    public @NotNull Player player() {
        return player;
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
