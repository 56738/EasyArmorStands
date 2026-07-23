package me.m56738.easyarmorstands.api.formatter;

import me.m56738.easyarmorstands.api.util.EasFormat;
import me.m56738.easyarmorstands.platform.util.Rotations;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class RotationsFormatter implements ValueFormatter<Rotations> {
    private static String formatAngle(double angle) {
        return EasFormat.ANGLE_FORMAT.format(angle);
    }

    @Override
    public Component format(Rotations value) {
        return Component.text()
                .append(Component.text(formatAngle(value.x()), NamedTextColor.RED))
                .append(Component.text(", "))
                .append(Component.text(formatAngle(value.y()), NamedTextColor.GREEN))
                .append(Component.text(", "))
                .append(Component.text(formatAngle(value.z()), NamedTextColor.BLUE))
                .build();
    }

    @Override
    public String formatAsString(Rotations value) {
        double x = value.x();
        double y = value.y();
        double z = value.z();
        return x + "," + y + "," + z;
    }
}
