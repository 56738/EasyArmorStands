package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public abstract class PropertyButton<T> implements MenuButton {
    protected final Property<T> property;
    private final MenuIcon icon;
    private final List<Component> description;

    public PropertyButton(Property<T> property, MenuIcon icon, List<Component> description) {
        this.property = property;
        this.icon = icon;
        this.description = description;
    }

    @Override
    public MenuIcon icon() {
        return icon;
    }

    @Override
    public Component name() {
        return property.getType().getName();
    }

    @Override
    public List<Component> description() {
        List<Component> description = new ArrayList<>();
        populateDescription(description);
        return description;
    }

    protected void populateDescription(List<Component> description) {
        description.add(property.getType().getValueComponent(property.getValue()));
        description.addAll(this.description);
    }
}
