package me.m56738.easyarmorstands.modded.api.element;

import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface DefaultEntityElement<E extends Entity> extends EntityElement<E> {
    @Override
    @NotNull PropertyRegistry getProperties();
}
