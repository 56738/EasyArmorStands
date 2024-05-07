package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ArmorStandPartInfo {
    private static final Map<ArmorStandPart, ArmorStandPartInfo> parts = new EnumMap<>(ArmorStandPart.class);

    static {
        parts.put(ArmorStandPart.HEAD, new ArmorStandPartInfo(
                "head",
                Message.component("easyarmorstands.armor-stand-part.head"),
                new Vector3d(0, 23, 0),
                new Vector3d(0, 7, 0)
        ));
        parts.put(ArmorStandPart.BODY, new ArmorStandPartInfo(
                "body",
                Message.component("easyarmorstands.armor-stand-part.body"),
                new Vector3d(0, 24, 0),
                new Vector3d(0, -12, 0)
        ));
        parts.put(ArmorStandPart.LEFT_ARM, new ArmorStandPartInfo(
                "left-arm",
                Message.component("easyarmorstands.armor-stand-part.left-arm"),
                new Vector3d(5, 22, 0),
                new Vector3d(0, -10, 0)
        ));
        parts.put(ArmorStandPart.RIGHT_ARM, new ArmorStandPartInfo(
                "right-arm",
                Message.component("easyarmorstands.armor-stand-part.right-arm"),
                new Vector3d(-5, 22, 0),
                new Vector3d(0, -10, 0)
        ));
        parts.put(ArmorStandPart.LEFT_LEG, new ArmorStandPartInfo(
                "left-leg",
                Message.component("easyarmorstands.armor-stand-part.left-leg"),
                new Vector3d(1.9, 12, 0),
                new Vector3d(0, -11, 0)
        ));
        parts.put(ArmorStandPart.RIGHT_LEG, new ArmorStandPartInfo(
                "right-leg",
                Message.component("easyarmorstands.armor-stand-part.right-leg"),
                new Vector3d(-1.9, 12, 0),
                new Vector3d(0, -11, 0)
        ));
    }

    private final String name;
    private final Component displayName;
    private final Vector3dc offset;
    private final Vector3dc length;
    private final Vector3dc smallOffset;
    private final Vector3dc smallLength;

    private ArmorStandPartInfo(String name, Component displayName, Vector3dc offset, Vector3dc length) {
        this.name = name;
        this.displayName = displayName;
        this.offset = offset.div(16, new Vector3d());
        this.length = length.div(16, new Vector3d());
        this.smallOffset = this.offset.div(2, new Vector3d());
        this.smallLength = this.length.div(2, new Vector3d());
    }

    @Contract(pure = true)
    public static @NotNull ArmorStandPartInfo of(ArmorStandPart part) {
        return Objects.requireNonNull(parts.get(part));
    }

    public String getName() {
        return name;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public Vector3dc getOffset(ArmorStandSize size, double scale) {
        Vector3dc offset = size.isSmall() ? smallOffset : this.offset;
        return mul(offset, scale);
    }

    public Vector3dc getLength(ArmorStandSize size, double scale) {
        Vector3dc length = size.isSmall() ? smallLength : this.length;
        return mul(length, scale);
    }

    private Vector3dc mul(Vector3dc vector, double scale) {
        if (scale == 1) {
            return vector;
        }
        return vector.mul(scale, new Vector3d());
    }
}
