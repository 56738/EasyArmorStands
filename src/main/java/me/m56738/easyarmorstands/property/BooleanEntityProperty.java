package me.m56738.easyarmorstands.property;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.BooleanArgument;
import me.m56738.easyarmorstands.command.EasCommandSender;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public abstract class BooleanEntityProperty<E extends Entity> extends ToggleEntityProperty<E, Boolean> {

    @Override
    public @NotNull CommandArgument<EasCommandSender, Boolean> getArgument() {
        return BooleanArgument.of(getName());
    }

    @Override
    public final Boolean getNextValue(E entity) {
        return !getValue(entity);
    }
}
