package me.m56738.easyarmorstands.property.v1_19_4.display;

import cloud.commandframework.arguments.parser.ArgumentParser;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.ResettableEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class DisplayLeftRotationProperty implements ResettableEntityProperty<Display, Quaternionfc> {
    private final JOMLMapper mapper;

    public DisplayLeftRotationProperty(JOMLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Quaternionfc getValue(Display entity) {
        return mapper.getLeftRotation(entity.getTransformation());
    }

    @Override
    public TypeToken<Quaternionfc> getValueType() {
        return TypeToken.get(Quaternionfc.class);
    }

    @Override
    public void setValue(Display entity, Quaternionfc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation(mapper.getTransformation(
                mapper.getTranslation(transformation),
                value,
                mapper.getScale(transformation),
                mapper.getRightRotation(transformation)));
    }

    @Override
    public @NotNull String getName() {
        return "rotation";
    }

    @Override
    public @NotNull Class<Display> getEntityType() {
        return Display.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Quaternionfc> getArgumentParser() {
        return null;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("rotation");
    }

    @Override
    public @NotNull Component getValueName(Quaternionfc value) {
        return Util.formatAngle(Util.toEuler(value.get(new Quaterniond())));
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.rotation";
    }

    @Override
    public Quaternionfc getResetValue() {
        return new Quaternionf();
    }
}
