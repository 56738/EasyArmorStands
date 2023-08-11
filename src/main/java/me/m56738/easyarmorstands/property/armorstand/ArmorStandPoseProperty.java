package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.key.PropertyKey;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class ArmorStandPoseProperty implements Property<Quaterniondc> {
    private static final Key KEY = EasyArmorStands.key("armor_stand_pose");
    private final ArmorStand entity;
    private final ArmorStandPart part;

    public ArmorStandPoseProperty(ArmorStand entity, ArmorStandPart part) {
        this.entity = entity;
        this.part = part;
    }

    public static PropertyKey<Quaterniondc> key(ArmorStandPart part) {
        return PropertyKey.of(KEY, part);
    }

    @Override
    public Quaterniondc getValue() {
        return Util.fromEuler(part.getPose(entity), new Quaterniond());
    }

    @Override
    public void setValue(Quaterniondc value) {
        part.setPose(entity, Util.toEuler(value));
    }

    @Override
    public Action createChangeAction(Quaterniondc oldValue, Quaterniondc value) {
        return new EntityPropertyAction<>(entity, e -> new ArmorStandPoseProperty(e, part), oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return part.getDisplayName();
    }

    @Override
    public @NotNull Component getValueComponent(Quaterniondc value) {
        return Util.formatAngle(Util.toEuler(value));
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.armorstand.pose." + part.getName();
    }

    // TODO /eas reset
//    @Override
//    public Quaterniondc getResetValue() {
//        return new Quaterniond();
//    }
}
