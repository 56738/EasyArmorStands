package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.text.Component;

public class IntegerPropertyType extends ConfigurablePropertyType<Integer> {
    public IntegerPropertyType(String key) {
        super(key);
    }

    @Override
    public Component getValueComponent(Integer value) {
        return Component.text(value);
    }
}
