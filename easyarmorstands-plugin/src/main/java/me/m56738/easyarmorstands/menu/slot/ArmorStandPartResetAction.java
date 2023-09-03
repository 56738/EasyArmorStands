package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import org.bukkit.util.EulerAngle;

import java.util.function.Consumer;

public class ArmorStandPartResetAction<T> implements Consumer<MenuClick> {
    private final Property<T> property;
    private final PropertyContainer container;
    private final T value;

    public ArmorStandPartResetAction(Property<T> property, PropertyContainer container, T value) {
        this.property = property;
        this.container = container;
        this.value = value;
    }

    public static ArmorStandPartResetAction<EulerAngle> pose(ArmorStandPart part, PropertyContainer container) {
        Property<EulerAngle> property = container.get(ArmorStandPropertyTypes.POSE.get(part));
        return new ArmorStandPartResetAction<>(property, container, EulerAngle.ZERO);
    }

    @Override
    public void accept(MenuClick click) {
        property.setValue(value);
        container.commit();
    }
}
