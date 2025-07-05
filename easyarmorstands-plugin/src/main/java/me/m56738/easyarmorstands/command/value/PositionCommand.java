package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.Command;
import me.m56738.easyarmorstands.lib.cloud.bukkit.parser.location.LocationParser;
import me.m56738.easyarmorstands.lib.cloud.description.Description;
import me.m56738.easyarmorstands.lib.cloud.minecraft.extras.RichDescription;
import me.m56738.easyarmorstands.lib.cloud.permission.Permission;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class PositionCommand extends PropertyCommand<Location> {
    public PositionCommand() {
        super(EntityPropertyTypes.LOCATION, LocationParser.locationParser());
    }

    @Override
    public Command.@NonNull Builder<EasCommandSender> applyToCommandBuilder(Command.@NonNull Builder<EasCommandSender> builder) {
        return builder.literal("position");
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Message.component("easyarmorstands.command.value.position");
    }

    @Override
    public @NotNull Permission getPermission() {
        return Permission.allOf(super.getPermission(), Permission.of(Permissions.POSITION));
    }

    @Override
    public @NotNull Description getShowDescription() {
        return RichDescription.translatable("easyarmorstands.command.description.position.show");
    }

    @Override
    public @NotNull Description getSetterDescription() {
        return RichDescription.translatable("easyarmorstands.command.description.position.set");
    }

    @Override
    public @NotNull String formatCommand(@NotNull Location value) {
        return "/eas position " + value.getX() + " " + value.getY() + " " + value.getZ();
    }

    @Override
    public boolean setValue(@NotNull PropertyContainer properties, @NotNull Location value) {
        Property<Location> property = properties.get(EntityPropertyTypes.LOCATION);
        Location location = property.getValue();
        location.setX(value.getX());
        location.setY(value.getY());
        location.setZ(value.getZ());
        return property.setValue(location);
    }

    @Override
    public void sendSuccess(@NotNull Audience audience, @NotNull Location value) {
        audience.sendMessage(Message.success("easyarmorstands.success.moved", Util.formatLocation(value)));
    }
}
