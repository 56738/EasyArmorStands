package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.property.Property;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class EnumHandler<T> extends ToggleHandler<T> {
    private final List<T> values;

    public EnumHandler(Property<T> property, List<T> values) {
        super(property);
        this.values = values;
    }

    public static <T> Function<Property<T>, EnumHandler<T>> provider(Collection<T> values) {
        return property -> new EnumHandler<>(property, List.copyOf(values));
    }

    public static <T> Function<Property<T>, EnumHandler<T>> provider(T[] values) {
        return EnumHandler.provider(List.of(values));
    }

    public static <T extends Enum<T>> Function<Property<T>, EnumHandler<T>> provider(Class<T> type) {
        return EnumHandler.provider(type.getEnumConstants());
    }

    @Override
    protected boolean toggleNextValue() {
        return toggleValue(1);
    }

    @Override
    protected boolean togglePreviousValue() {
        return toggleValue(-1);
    }

    protected boolean toggleValue(int offset) {
        int index = values.indexOf(property.getValue());
        index += offset;
        if (index == values.size()) {
            index = 0;
        } else if (index == -1) {
            index += values.size();
        }
        return property.setValue(values.get(index));
    }
}
