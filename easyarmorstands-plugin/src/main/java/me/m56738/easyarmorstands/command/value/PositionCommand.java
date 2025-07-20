package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperLocationAdapter;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class PositionCommand extends PropertyCommand<Location> {
    public PositionCommand(CommonPlatform platform) {
        super(EntityPropertyTypes.LOCATION, platform.getLocationParser());
    }

    @Override
    public Command.@NonNull Builder<Source> applyToCommandBuilder(Command.@NonNull Builder<Source> builder) {
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
        Vector3dc position = value.position();
        return "/eas position " + position.x() + " " + position.y() + " " + position.z();
    }

    @Override
    public boolean setValue(@NotNull PropertyContainer properties, @NotNull Location value) {
        Property<Location> property = properties.get(EntityPropertyTypes.LOCATION);
        return property.setValue(property.getValue().withPosition(value.position()));
    }

    @Override
    public void sendSuccess(@NotNull Audience audience, @NotNull Location value) {
        audience.sendMessage(Message.success("easyarmorstands.success.moved", Util.formatLocation(PaperLocationAdapter.toNative(value))));
    }
}
