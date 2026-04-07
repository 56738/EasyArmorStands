package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
public class GravityToggleButton extends BooleanToggleButton {
    private final @Nullable Property<Boolean> canTickProperty;
    private final List<Component> canTickWarning;

    public GravityToggleButton(Property<Boolean> property, @Nullable Property<Boolean> canTickProperty, MenuIcon icon, List<Component> description, List<Component> canTickWarning) {
        super(property, icon, description);
        this.canTickProperty = canTickProperty;
        this.canTickWarning = canTickWarning;
    }

    @Override
    protected void populateDescription(List<Component> description) {
        if (canTickProperty != null && property.getValue() && !canTickProperty.getValue()) {
            description.addAll(canTickWarning);
        }
    }

    @Override
    public Boolean getNextValue() {
        boolean value = property.getValue();
        if (canTickProperty != null && value && !canTickProperty.getValue()) {
            return true;
        }
        return !value;
    }

    @Override
    protected boolean setValue(Boolean value) {
        if (!super.setValue(value)) {
            return false;
        }
        if (canTickProperty != null && value) {
            canTickProperty.setValue(true);
        }
        return true;
    }
}
