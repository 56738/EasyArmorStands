package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.parser.ArgumentParserProvider;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class PositionCommand extends PropertyCommand<Location> {
    public PositionCommand(ArgumentParserProvider parserProvider) {
        super("position", EntityPropertyTypes.LOCATION, parserProvider.locationParser());
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
        audience.sendMessage(Message.success("easyarmorstands.success.moved", Util.formatLocation(value)));
    }
}
