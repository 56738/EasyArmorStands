package me.m56738.easyarmorstands.element;

import org.bukkit.entity.Entity;

public class MannequinElement<E extends Entity> extends SimpleEntityElement<E> {
    public MannequinElement(E entity, SimpleEntityElementType<E> type) {
        super(entity, type);
    }
}
