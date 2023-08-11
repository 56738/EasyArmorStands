package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

import java.util.EnumMap;

public class ArmorStandPoseProperty implements Property<Quaterniondc> {
    private static final EnumMap<ArmorStandPart, PropertyType<Quaterniondc>> TYPES = new EnumMap<>(ArmorStandPart.class);

    static {
        for (ArmorStandPart part : ArmorStandPart.values()) {
            TYPES.put(part, new Type(part));
        }
    }

    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final PropertyType<Quaterniondc> type;

    public ArmorStandPoseProperty(ArmorStand entity, ArmorStandPart part) {
        this.entity = entity;
        this.part = part;
        this.type = TYPES.get(part);
    }

    public static PropertyType<Quaterniondc> type(ArmorStandPart part) {
        return TYPES.get(part);
    }

    @Override
    public PropertyType<Quaterniondc> getType() {
        return type;
    }

    @Override
    public Quaterniondc getValue() {
        return Util.fromEuler(part.getPose(entity), new Quaterniond());
    }

    @Override
    public boolean setValue(Quaterniondc value) {
        part.setPose(entity, Util.toEuler(value));
        return true;
    }

    // TODO /eas reset
//    @Override
//    public Quaterniondc getResetValue() {
//        return new Quaterniond();
//    }

    private static class Type implements PropertyType<Quaterniondc> {
        private final ArmorStandPart part;
        private final String permission;

        private Type(ArmorStandPart part) {
            this.part = part;
            this.permission = "easyarmorstands.property.armorstand.pose." + part.getName();
        }

        @Override
        public String getPermission() {
            return permission;
        }

        @Override
        public Component getDisplayName() {
            return part.getDisplayName();
        }

        @Override
        public @NotNull Component getValueComponent(Quaterniondc value) {
            return Util.formatAngle(Util.toEuler(value));
        }
    }
}
