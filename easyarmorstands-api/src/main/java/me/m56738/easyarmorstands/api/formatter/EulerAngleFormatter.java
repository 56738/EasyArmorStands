package me.m56738.easyarmorstands.api.formatter;

import me.m56738.easyarmorstands.api.util.EasFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.util.EulerAngle;

public class EulerAngleFormatter implements ValueFormatter<EulerAngle> {
    @Override
    public Component format(EulerAngle value) {
        return Component.text()
                .append(Component.text(formatAngle(value.getX()), NamedTextColor.RED))
                .append(Component.text(", "))
                .append(Component.text(formatAngle(value.getY()), NamedTextColor.GREEN))
                .append(Component.text(", "))
                .append(Component.text(formatAngle(value.getZ()), NamedTextColor.BLUE))
                .build();
    }

    @Override
    public String formatAsString(EulerAngle value) {
        double x = Math.toDegrees(value.getX());
        double y = Math.toDegrees(value.getY());
        double z = Math.toDegrees(value.getZ());
        return x + "," + y + "," + z;
    }

    private static String formatAngle(double angle) {
        return EasFormat.ANGLE_FORMAT.format(Math.toDegrees(angle));
    }
}
