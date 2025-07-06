package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuContext;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class SimpleMenuContext implements MenuContext {
    private final Player player;
    private final Session session;
    private final Locale locale;

    public SimpleMenuContext(Player player) {
        this.player = player;
        this.session = EasyArmorStandsPlugin.getInstance().sessionManager().getSession(player);
        this.locale = player.locale();
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
        return null;
    }

    @Override
    public @NotNull Locale locale() {
        return locale;
    }

    @Override
    public @NotNull TagResolver resolver() {
        return TagResolver.empty();
    }

    @Override
    public @Nullable ColorPickerContext colorPicker() {
        return null;
    }
}
