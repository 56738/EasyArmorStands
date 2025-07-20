package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.api.platform.world.Location;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class EasFormat {
    public static final NumberFormat POSITION_FORMAT = new DecimalFormat("0.0000");
    public static final NumberFormat ANGLE_FORMAT = new DecimalFormat("+0.00°;-0.00°");
    public static final NumberFormat SCALE_FORMAT = new DecimalFormat("0.0000");

    private EasFormat() {
    }

    private static @NotNull Component format3D(@NotNull Vector3dc vector, @NotNull NumberFormat format) {
        return Component.text()
                .append(Component.text(format.format(vector.x()), NamedTextColor.RED))
                .append(Component.text(", "))
                .append(Component.text(format.format(vector.y()), NamedTextColor.GREEN))
                .append(Component.text(", "))
                .append(Component.text(format.format(vector.z()), NamedTextColor.BLUE))
                .build();
    }

    public static @NotNull Component formatPosition(@NotNull Vector3dc position) {
        return format3D(position, POSITION_FORMAT);
    }

    public static @NotNull Component formatLocation(@NotNull Location location) {
        return formatPosition(location.position());
    }

    public static @NotNull Component formatDegrees(double degrees) {
        return Component.text(ANGLE_FORMAT.format(EasMath.wrapDegrees(degrees)));
    }

    public static @NotNull Component formatScale(double scale) {
        return Component.text(SCALE_FORMAT.format(scale));
    }
}
