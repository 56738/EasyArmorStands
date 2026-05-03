package me.m56738.easyarmorstands.api.formatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Quaternionfc;

public class QuaternionfcFormatter implements ValueFormatter<Quaternionfc> {
    @Override
    public Component format(Quaternionfc value) {
        return Component.text()
                .append(Component.text(value.x(), NamedTextColor.RED))
                .append(Component.text(", "))
                .append(Component.text(value.y(), NamedTextColor.GREEN))
                .append(Component.text(", "))
                .append(Component.text(value.z(), NamedTextColor.BLUE))
                .append(Component.text(", "))
                .append(Component.text(value.w(), NamedTextColor.WHITE))
                .build();
    }

    @Override
    public String formatAsString(Quaternionfc value) {
        return value.x() + "," + value.y() + "," + value.z() + "," + value.w();
    }
}
