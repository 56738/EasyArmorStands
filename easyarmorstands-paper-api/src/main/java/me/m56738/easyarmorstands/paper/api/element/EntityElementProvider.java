package me.m56738.easyarmorstands.paper.api.element;

import me.m56738.easyarmorstands.api.element.Element;
import org.bukkit.entity.Entity;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface EntityElementProvider {
    @Nullable
    Element getElement(Entity entity);
}
