package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class BooleanToggleButton extends ToggleButton<Boolean> {
    public BooleanToggleButton(Property<Boolean> property, MenuIcon icon, List<Component> description) {
        super(property, icon, description);
    }

    @Override
    public Boolean getNextValue() {
        return !property.getValue();
    }

    @Override
    public Boolean getPreviousValue() {
        return getNextValue();
    }
}
