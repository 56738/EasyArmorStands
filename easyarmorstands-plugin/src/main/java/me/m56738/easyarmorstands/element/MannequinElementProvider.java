package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.capability.mannequin.MannequinCapability;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MannequinElementProvider<E extends Entity> implements EntityElementProvider {
    private final MannequinElementType<E> type;
    private final MannequinCapability capability;

    public MannequinElementProvider(MannequinElementType<E> type, MannequinCapability capability) {
        this.type = type;
        this.capability = capability;
    }

    @Override
    public @Nullable Element getElement(@NotNull Entity entity) {
        if (capability.isMannequin(entity)) {
            return type.getElement(type.getEntityClass().cast(entity));
        }
        return null;
    }
}
