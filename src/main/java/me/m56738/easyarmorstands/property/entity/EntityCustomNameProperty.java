package me.m56738.easyarmorstands.property.entity;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.StringArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class EntityCustomNameProperty implements EntityProperty<Entity, Component> {
    private final ComponentCapability componentCapability;

    public EntityCustomNameProperty(ComponentCapability componentCapability) {
        this.componentCapability = componentCapability;
    }

    @Override
    public Component getValue(Entity entity) {
        Component name = componentCapability.getCustomName(entity);
        if (name == null) {
            name = Component.empty();
        }
        return name;
    }

    @Override
    public TypeToken<Component> getValueType() {
        return TypeToken.get(Component.class);
    }

    @Override
    public void setValue(Entity entity, Component value) {
        componentCapability.setCustomName(entity, value);
    }

    @Override
    public @NotNull String getName() {
        return "name";
    }

    @Override
    public @NotNull Class<Entity> getEntityType() {
        return Entity.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Component> getArgumentParser() {
        return new StringArgument.StringParser<EasCommandSender>(
                StringArgument.StringMode.GREEDY,
                (v1, v2) -> Collections.emptyList()
        ).map((ctx, input) ->
                ArgumentParseResult.success(MiniMessage.miniMessage().deserialize(input)));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("custom name");
    }

    @Override
    public @NotNull Component getValueName(Component value) {
        return value;
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.name";
    }
}
