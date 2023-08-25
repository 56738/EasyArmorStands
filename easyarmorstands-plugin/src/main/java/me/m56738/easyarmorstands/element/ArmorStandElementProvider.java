package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class ArmorStandElementProvider implements EntityElementProvider {
    private final ArmorStandElementType type;

    public ArmorStandElementProvider(ArmorStandElementType type) {
        this.type = type;
    }

    @Override
    public @Nullable Element getElement(Entity entity) {
        if (entity instanceof ArmorStand) {
            return type.getElement((ArmorStand) entity);
        }
        return null;
    }
}
