package me.m56738.easyarmorstands.api.formatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public record BooleanFormatter(Component trueName, Component falseName) implements ValueFormatter<Boolean> {
    private static final BooleanFormatter TOGGLE = BooleanFormatter.translatable(
            "easyarmorstands.property.common.enabled",
            "easyarmorstands.property.common.disabled");
    private static final BooleanFormatter VISIBILITY = BooleanFormatter.translatable(
            "easyarmorstands.property.common.visible",
            "easyarmorstands.property.common.invisible");

    public static BooleanFormatter toggle() {
        return TOGGLE;
    }

    public static BooleanFormatter visibility() {
        return VISIBILITY;
    }

    public static BooleanFormatter translatable(String trueKey, String falseKey) {
        return new BooleanFormatter(
                Component.translatable(trueKey, NamedTextColor.GREEN),
                Component.translatable(falseKey, NamedTextColor.RED));
    }

    public BooleanFormatter inverted() {
        return new BooleanFormatter(falseName, trueName);
    }

    @Override
    public Component format(Boolean value) {
        return value ? trueName : falseName;
    }
}
