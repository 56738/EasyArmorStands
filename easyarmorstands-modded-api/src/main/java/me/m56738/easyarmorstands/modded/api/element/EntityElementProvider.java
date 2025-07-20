package me.m56738.easyarmorstands.modded.api.element;

import me.m56738.easyarmorstands.api.element.Element;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface EntityElementProvider {
    @Nullable
    Element getElement(Entity entity);
}
