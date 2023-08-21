package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.element.EntityElementProvider;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class DisplayElementProvider<T extends Display> implements EntityElementProvider {
    private final DisplayElementType<T> type;

    public DisplayElementProvider(DisplayElementType<T> type) {
        this.type = type;
    }

    @Override
    public @Nullable Element getElement(Entity entity) {
        if (type.getEntityType().isInstance(entity)) {
            return type.getElement(type.getEntityType().cast(entity));
        }
        return null;
    }

    public DisplayElementType<T> getType() {
        return type;
    }
}
