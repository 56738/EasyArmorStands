package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class EnumToggleButton<T extends Enum<T>> extends ToggleButton<T> {
    private final T[] values;

    public EnumToggleButton(Property<T> property, MenuIcon icon, List<Component> description, T[] values) {
        super(property, icon, description);
        this.values = values;
    }

    private T getNeighbour(int offset) {
        int index = ArrayUtils.indexOf(values, property.getValue());
        if (index == -1) {
            return values[0];
        }
        index += offset;
        if (index >= values.length) {
            index -= values.length;
        } else if (index < 0) {
            index += values.length;
        }
        return values[index];
    }

    @Override
    public T getNextValue() {
        return getNeighbour(1);
    }

    @Override
    public T getPreviousValue() {
        return getNeighbour(-1);
    }
}
