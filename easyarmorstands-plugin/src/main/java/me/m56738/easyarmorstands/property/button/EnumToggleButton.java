package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;

public class EnumToggleButton<T extends Enum<T>> extends ToggleButton<T> {
    private final T[] values;

    public EnumToggleButton(Element element, PropertyType<T> type, SimpleItemTemplate item, T[] values) {
        super(element, type, item);
        this.values = values;
    }

    private T getNeighbour(int offset) {
        int index = getUntrackedProperty().getValue().ordinal() + offset;
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
