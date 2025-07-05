package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.processor.PropertyPermissionPredicate;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.Command;
import me.m56738.easyarmorstands.lib.cloud.description.Description;
import me.m56738.easyarmorstands.lib.cloud.minecraft.extras.RichDescription;
import me.m56738.easyarmorstands.lib.cloud.parser.ParserDescriptor;
import me.m56738.easyarmorstands.lib.cloud.parser.standard.FloatParser;
import me.m56738.easyarmorstands.lib.cloud.permission.Permission;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class PitchCommand implements ValueCommand<Float> {
    @Override
    public Command.@NonNull Builder<EasCommandSender> applyToCommandBuilder(Command.@NonNull Builder<EasCommandSender> builder) {
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
    public @NotNull ParserDescriptor<EasCommandSender, Float> getParser() {
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
        return properties.get(EntityPropertyTypes.LOCATION).getValue().getPitch();
    }

    @Override
    public boolean setValue(@NotNull PropertyContainer properties, @NotNull Float value) {
        Property<Location> property = properties.get(EntityPropertyTypes.LOCATION);
        Location location = property.getValue();
        location.setPitch(value);
        return property.setValue(location);
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
