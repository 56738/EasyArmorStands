package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.processor.PropertyPermissionPredicate;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.parser.standard.FloatParser;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public abstract class ScaleAxisCommand implements ValueCommand<Float> {
    private final PropertyType<Vector3fc> type;
    private final Axis axis;

    public ScaleAxisCommand(PropertyType<Vector3fc> type, Axis axis) {
        this.type = type;
        this.axis = axis;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return type.getName()
                .append(Component.space())
                .append(Component.text(axis.getName(), TextColor.color(axis.getColor())));
    }

    @Override
    public @NotNull Permission getPermission() {
        return PropertyPermissionPredicate.createPermission(type);
    }

    @Override
    public @NotNull ParserDescriptor<CommandSource, Float> getParser() {
        return FloatParser.floatParser(0);
    }

    @Override
    public @NotNull Description getShowDescription() {
        return RichDescription.translatable("easyarmorstands.command.description.scale.show");
    }

    @Override
    public @NotNull Description getSetterDescription() {
        return RichDescription.translatable("easyarmorstands.command.description.scale.set");
    }

    @Override
    public boolean isSupported(@NotNull PropertyContainer properties) {
        return properties.getOrNull(type) != null;
    }

    @Override
    public @NotNull Float getValue(@NotNull PropertyContainer properties) {
        return axis.getValue(properties.get(type).getValue());
    }

    @Override
    public boolean setValue(@NotNull PropertyContainer properties, @NotNull Float value) {
        Property<Vector3fc> property = properties.get(type);
        Vector3f scale = new Vector3f(property.getValue());
        axis.setValue(scale, value);
        return property.setValue(scale);
    }

    @Override
    public @NotNull Component formatValue(@NotNull Float value) {
        return Util.formatScale(value);
    }

    @Override
    public void sendSuccess(@NotNull Audience audience, @NotNull Float value) {
        audience.sendMessage(Message.success("easyarmorstands.success.changed-scale.axis",
                Component.text(axis.getName()), Util.formatScale(value)));
    }

    @Override
    public void sendNotSupported(@NotNull Audience audience) {
        audience.sendMessage(Message.error("easyarmorstands.error.scale-unsupported"));
    }
}
