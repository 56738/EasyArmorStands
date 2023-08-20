package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.util.ItemTemplate;

public abstract class ToggleButton<T> extends SimpleButton<T> {
    public ToggleButton(Property<T> property, PropertyContainer container, ItemTemplate item) {
        super(property, container, item);
    }

    public abstract T getNextValue();

    public abstract T getPreviousValue();

    protected boolean setValue(T value) {
        return property.setValue(value);
    }

    @Override
    public void onClick(MenuClick click) {
        boolean changed;
        if (click.isLeftClick()) {
            changed = setValue(getNextValue());
        } else if (click.isRightClick()) {
            changed = setValue(getPreviousValue());
        } else {
            return;
        }
        if (changed) {
            container.commit();
        }
    }
}
