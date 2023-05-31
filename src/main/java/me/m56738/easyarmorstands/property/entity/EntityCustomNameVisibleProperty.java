package me.m56738.easyarmorstands.property.entity;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.BooleanArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
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
    public TypeToken<Boolean> getValueType() {
        return TypeToken.get(Boolean.class);
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
    public ArgumentParser<EasCommandSender, Boolean> getArgumentParser() {
        return new BooleanArgument.BooleanParser<>(true);
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
    public @NotNull String getValueClipboardContent(Boolean value) {
        return Boolean.toString(value);
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.name.visible";
    }
}
