package me.m56738.easyarmorstands.property.entity;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.bukkit.parsers.location.LocationArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityLocationProperty implements EntityProperty<Entity, Location> {
    @Override
    public Location getValue(Entity entity) {
        return entity.getLocation();
    }

    @Override
    public TypeToken<Location> getValueType() {
        return TypeToken.get(Location.class);
    }

    @Override
    public void setValue(Entity entity, Location value) {
        entity.teleport(value);
    }

    @Override
    public @NotNull String getName() {
        return "location";
    }

    @Override
    public @NotNull Class<Entity> getEntityType() {
        return Entity.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Location> getArgumentParser() {
        return new LocationArgument.LocationParser<>();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("position");
    }

    @Override
    public @NotNull Component getValueName(Location value) {
        return Util.formatLocation(value);
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.location";
    }
}
