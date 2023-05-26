package me.m56738.easyarmorstands.property.armorstand;

import cloud.commandframework.arguments.compound.ArgumentTriplet;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.types.tuples.Triplet;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class ArmorStandPoseProperty implements EntityProperty<ArmorStand, Quaterniondc> {
    private final ArmorStandPart part;

    public ArmorStandPoseProperty(ArmorStandPart part) {
        this.part = part;
    }

    @Override
    public Quaterniondc getValue(ArmorStand entity) {
        return Util.fromEuler(part.getPose(entity), new Quaterniond());
    }

    @Override
    public TypeToken<Quaterniondc> getValueType() {
        return TypeToken.get(Quaterniondc.class);
    }

    @Override
    public void setValue(ArmorStand entity, Quaterniondc value) {
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
    public ArgumentParser<EasCommandSender, Quaterniondc> getArgumentParser() {
        return ArgumentTriplet.of(EasyArmorStands.getInstance().getCommandManager(),
                getName(),
                Triplet.of("x", "y", "z"),
                Triplet.of(double.class, double.class, double.class)
        ).withMapper(Quaterniondc.class, (sender, triplet) -> Util.fromEuler(
                new EulerAngle(
                        Math.toRadians(triplet.getFirst()),
                        Math.toRadians(triplet.getSecond()),
                        Math.toRadians(triplet.getThird())),
                new Quaterniond())).getParser();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return part.getDisplayName();
    }

    @Override
    public @NotNull Component getValueName(Quaterniondc value) {
        return Util.formatAngle(Util.toEuler(value));
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property." + part.getName();
    }
}
