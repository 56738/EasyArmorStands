package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.processor.PropertyPermissionPredicate;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.description.Description;
import me.m56738.easyarmorstands.lib.cloud.minecraft.extras.RichDescription;
import me.m56738.easyarmorstands.lib.cloud.parser.ParserDescriptor;
import me.m56738.easyarmorstands.lib.cloud.parser.standard.FloatParser;
import me.m56738.easyarmorstands.lib.cloud.permission.Permission;
import me.m56738.easyarmorstands.lib.joml.Vector3f;
import me.m56738.easyarmorstands.lib.joml.Vector3fc;
import me.m56738.easyarmorstands.lib.kyori.adventure.audience.Audience;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.TextColor;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull ParserDescriptor<EasCommandSender, Float> getParser() {
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
