package me.m56738.easyarmorstands.property.entity;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.BooleanArgument;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCustomNameVisibleProperty implements EntityProperty<Entity, Boolean> {
    @Override
    public Boolean getValue(Entity entity) {
        return entity.isCustomNameVisible();
    }

    @Override
    public void setValue(Entity entity, Boolean value) {
        entity.setCustomNameVisible(value);
    }

    @Override
    public @NotNull String getName() {
        return "namevisible";
    }

    @Override
    public @NotNull Class<Entity> getEntityType() {
        return Entity.class;
    }

    @Override
    public @NotNull CommandArgument<EasCommandSender, Boolean> getArgument() {
        return BooleanArgument.of(getName());
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("custom name visible");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return Component.text(value);
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.name.visible";
    }
}
