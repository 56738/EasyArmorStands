package me.m56738.easyarmorstands.modded.api.element;

import me.m56738.easyarmorstands.api.element.Element;
import net.minecraft.world.entity.Entity;

public interface EntityElement<E extends Entity> extends Element {
    E getEntity();
}
