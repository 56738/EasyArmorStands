package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.processor.PropertyPermissionPredicate;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.parser.standard.FloatParser;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PitchCommand implements ValueCommand<Float> {
    @Override
    public Command.@NonNull Builder<Source> applyToCommandBuilder(Command.@NonNull Builder<Source> builder) {
        return builder.literal("pitch");
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Message.component("easyarmorstands.command.value.pitch");
    }

    @Override
    public @NotNull Permission getPermission() {
        return PropertyPermissionPredicate.createPermission(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @NotNull ParserDescriptor<Source, Float> getParser() {
        return FloatParser.floatParser();
    }

    @Override
    public @NotNull Description getShowDescription() {
        return RichDescription.translatable("easyarmorstands.command.description.pitch.show");
    }

    @Override
    public @NotNull Description getSetterDescription() {
        return RichDescription.translatable("easyarmorstands.command.description.pitch.set");
    }

    @Override
    public @NotNull Float getValue(@NotNull PropertyContainer properties) {
        return properties.get(EntityPropertyTypes.LOCATION).getValue().pitch();
    }

    @Override
    public boolean setValue(@NotNull PropertyContainer properties, @NotNull Float value) {
        Property<Location> property = properties.get(EntityPropertyTypes.LOCATION);
        return property.setValue(property.getValue().withPitch(value));
    }

    @Override
    public @NotNull Component formatValue(@NotNull Float value) {
        return Util.formatDegrees(value);
    }

    @Override
    public @NotNull String formatCommand(@NotNull Float value) {
        return "/eas pitch " + value;
    }

    @Override
    public void sendSuccess(@NotNull Audience audience, @NotNull Float value) {
        audience.sendMessage(Message.success("easyarmorstands.success.changed-pitch", Util.formatDegrees(value)));
    }
}
