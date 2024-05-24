package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.message.Message;
import org.jetbrains.annotations.NotNull;

public abstract class ToggleButton<T> extends PropertyButton<T> {
    public ToggleButton(Property<T> property, PropertyContainer container, SimpleItemTemplate item) {
        super(property, container, item);
    }

    public abstract T getNextValue();

    public abstract T getPreviousValue();

    protected boolean setValue(T value) {
        return property.setValue(value);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
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
            click.updateItem();
        } else {
            click.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }
}
