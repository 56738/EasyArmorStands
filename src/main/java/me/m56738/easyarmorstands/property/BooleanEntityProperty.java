package me.m56738.easyarmorstands.property;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.BooleanArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.EasCommandSender;
import org.bukkit.entity.Entity;

public abstract class BooleanEntityProperty<E extends Entity> extends ToggleEntityProperty<E, Boolean> {
    @Override
    public TypeToken<Boolean> getValueType() {
        return TypeToken.get(Boolean.class);
    }

    @Override
    public ArgumentParser<EasCommandSender, Boolean> getArgumentParser() {
        return new BooleanArgument.BooleanParser<>(true);
    }

    @Override
    public final Boolean getNextValue(E entity) {
        return !getValue(entity);
    }
}
