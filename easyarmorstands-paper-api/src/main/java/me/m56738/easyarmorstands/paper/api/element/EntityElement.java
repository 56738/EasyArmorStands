package me.m56738.easyarmorstands.paper.api.element;

import me.m56738.easyarmorstands.api.element.Element;
import org.bukkit.entity.Entity;

public interface EntityElement<E extends Entity> extends Element {
    E getEntity();
}
