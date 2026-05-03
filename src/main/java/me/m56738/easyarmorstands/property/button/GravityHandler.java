package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public class GravityHandler extends BooleanHandler {
    private final Property<Boolean> canTickProperty;

    public GravityHandler(Property<Boolean> property, Property<Boolean> canTickProperty) {
        super(property);
        this.canTickProperty = canTickProperty;
    }

    private boolean isMisconfigured() {
        // gravity enabled but ticking disabled
        return property.getValue() && !canTickProperty.getValue();
    }

    @Override
    protected boolean toggleNextValue() {
        if (isMisconfigured()) {
            return canTickProperty.setValue(true);
        }
        return super.toggleNextValue();
    }

    @Override
    protected boolean togglePreviousValue() {
        return super.toggleNextValue();
    }

    @Override
    public void modifyDescription(List<Component> description) {
        if (isMisconfigured()) {
            description.add(Component.translatable("easyarmorstands.property.gravity.can-tick-warning", NamedTextColor.YELLOW));
        }
    }

    @Override
    protected void handlePropertyShiftClick(Clipboard clipboard) {
        super.handlePropertyShiftClick(clipboard);
        clipboard.handlePropertyShiftClick(canTickProperty);
    }
}
