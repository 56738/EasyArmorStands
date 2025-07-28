package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.platform.entity.Entity;

import java.util.Objects;

public class EntityElementDiscoveryEntry implements ElementDiscoveryEntry {
    private final EntityElementDiscoverySource source;
    private final Entity entity;

    public EntityElementDiscoveryEntry(EntityElementDiscoverySource source, Entity entity) {
        this.source = source;
        this.entity = entity;
    }

    @Override
    public SelectableElement getElement() {
        Element element = source.getElement(entity);
        if (element instanceof SelectableElement) {
            return (SelectableElement) element;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityElementDiscoveryEntry that = (EntityElementDiscoveryEntry) o;
        return Objects.equals(source, that.source) && Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, entity);
    }
}
