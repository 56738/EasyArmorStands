package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertyButtonBuilder<T> {
    private final Property<T> property;
    private final List<Component> description = new ArrayList<>();
    private MenuIcon icon = MenuIcon.of(Material.STONE);
    private @Nullable ButtonHandler handler;

    public PropertyButtonBuilder(Property<T> property) {
        this.property = property;
    }

    public PropertyButtonBuilder<T> handler(ButtonHandler handler) {
        this.handler = handler;
        return this;
    }

    public PropertyButtonBuilder<T> icon(MenuIcon icon) {
        this.icon = icon;
        return this;
    }

    public PropertyButtonBuilder<T> description(Component... description) {
        this.description.addAll(List.of(description));
        return this;
    }

    public MenuButton build() {
        Objects.requireNonNull(handler, "handler");
        return new PropertyButton<>(property, handler, icon, description);
    }
}
