package me.m56738.easyarmorstands.property;

import io.leangen.geantyref.TypeToken;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Deprecated
public abstract class BooleanEntityProperty<E extends Entity> extends ToggleEntityProperty<E, Boolean> {
    @Override
    public TypeToken<Boolean> getValueType() {
        return TypeToken.get(Boolean.class);
    }

    @Override
    public @NotNull String getValueClipboardContent(Boolean value) {
        return Boolean.toString(value);
    }

    @Override
    public final Boolean getNextValue(E entity) {
        return !getValue(entity);
    }
}
