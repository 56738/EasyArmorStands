package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class PropertyButton<T> implements MenuButton {
    private final Property<T> property;
    private final ButtonHandler handler;
    private final MenuIcon icon;
    private final List<Component> description;

    public PropertyButton(Property<T> property, ButtonHandler handler, MenuIcon icon, List<Component> description) {
        this.property = property;
        this.handler = handler;
        this.icon = icon;
        this.description = description;
    }

    @Override
    public Key key() {
        return property.getType().key();
    }

    @Override
    public MenuIcon icon() {
        return handler.modifyIcon(icon);
    }

    @Override
    public Component name() {
        return property.getType().getName();
    }

    @Override
    public List<Component> description() {
        List<Component> description = new ArrayList<>();
        description.add(property.getType().getValueComponent(property.getValue()));
        description.addAll(this.description);
        handler.modifyDescription(description);
        return description;
    }

    @Override
    public void onClick(MenuClickContext context) {
        handler.onClick(context);
    }
}
