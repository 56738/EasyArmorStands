package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmorStandElementProvider implements EntityElementProvider {
    private final ArmorStandElementType type;

    public ArmorStandElementProvider(ArmorStandElementType type) {
        this.type = type;
    }

    @Override
    public @Nullable Element getElement(@NotNull Entity entity) {
        if (type.getEntityType().equals(entity.getType())) {
            return type.getElement(entity);
        }
        return null;
    }
}
