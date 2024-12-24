package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface EntityElementReference<E extends Entity> extends ElementReference {
    static <E extends Entity> EntityElementReference<E> of(EntityElementType<E> type, E entity) {
        return EasyArmorStands.get().createReference(type, entity);
    }

    @Override
    @NotNull EntityElementType<E> getType();

    @Override
    @Nullable Element getElement();

    @Contract(pure = true)
    @NotNull UUID getId();
}
