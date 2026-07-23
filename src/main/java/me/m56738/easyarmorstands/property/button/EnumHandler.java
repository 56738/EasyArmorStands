package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class EnumHandler<T> extends ToggleHandler<T> {
    private final List<T> values;

    public EnumHandler(EasyArmorStandsCommon eas, Property<T> property, List<T> values) {
        super(eas, property);
        this.values = values;
    }

    public static <T> Function<Property<T>, EnumHandler<T>> provider(EasyArmorStandsCommon eas, Collection<T> values) {
        return property -> new EnumHandler<>(eas, property, List.copyOf(values));
    }

    public static <T> Function<Property<T>, EnumHandler<T>> provider(EasyArmorStandsCommon eas, T[] values) {
        return EnumHandler.provider(eas, List.of(values));
    }

    public static <T extends Enum<T>> Function<Property<T>, EnumHandler<T>> provider(EasyArmorStandsCommon eas, Class<T> type) {
        return EnumHandler.provider(eas, type.getEnumConstants());
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
