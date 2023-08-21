package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypes;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class ArmorStandPoseProperty implements Property<Quaterniondc> {
    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final PropertyType<Quaterniondc> type;

    public ArmorStandPoseProperty(ArmorStand entity, ArmorStandPart part) {
        this.entity = entity;
        this.part = part;
        this.type = PropertyTypes.ARMOR_STAND_POSE.get(part);
    }

    @Override
    public @NotNull PropertyType<Quaterniondc> getType() {
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
}
