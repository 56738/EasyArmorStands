package me.m56738.easyarmorstands.property.armorstand;

import cloud.commandframework.arguments.compound.ArgumentTriplet;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.types.tuples.Triplet;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;

public class ArmorStandPoseProperty implements EntityProperty<ArmorStand, Matrix3dc> {
    private final ArmorStandPart part;

    public ArmorStandPoseProperty(ArmorStandPart part) {
        this.part = part;
    }

    @Override
    public Matrix3dc getValue(ArmorStand entity) {
        return Util.fromEuler(part.getPose(entity), new Matrix3d());
    }

    @Override
    public TypeToken<Matrix3dc> getValueType() {
        return TypeToken.get(Matrix3dc.class);
    }

    @Override
    public void setValue(ArmorStand entity, Matrix3dc value) {
        part.setPose(entity, Util.toEuler(value));
    }

    @Override
    public @NotNull String getName() {
        return part.getName();
    }

    @Override
    public @NotNull Class<ArmorStand> getEntityType() {
        return ArmorStand.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Matrix3dc> getArgumentParser() {
        return ArgumentTriplet.of(EasyArmorStands.getInstance().getCommandManager(),
                getName(),
                Triplet.of("x", "y", "z"),
                Triplet.of(double.class, double.class, double.class)
        ).withMapper(Matrix3dc.class, (sender, triplet) -> Util.fromEuler(
                new EulerAngle(
                        Math.toRadians(triplet.getFirst()),
                        Math.toRadians(triplet.getSecond()),
                        Math.toRadians(triplet.getThird())),
                new Matrix3d())).getParser();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return part.getDisplayName();
    }

    @Override
    public @NotNull Component getValueName(Matrix3dc value) {
        return Util.formatAngle(Util.toEuler(value));
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property." + part.getName();
    }
}
