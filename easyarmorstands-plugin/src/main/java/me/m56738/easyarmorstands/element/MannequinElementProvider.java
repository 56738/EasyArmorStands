package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mannequin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MannequinElementProvider implements EntityElementProvider {
    private final MannequinElementType type;

    public MannequinElementProvider(MannequinElementType type) {
        this.type = type;
    }

    @Override
    public @Nullable Element getElement(@NotNull Entity entity) {
        if (entity instanceof Mannequin) {
            return type.getElement((Mannequin) entity);
        }
        return null;
    }
}
