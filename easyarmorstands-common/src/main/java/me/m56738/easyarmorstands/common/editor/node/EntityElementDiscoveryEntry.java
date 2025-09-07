package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class EntityElementDiscoveryEntry implements ElementDiscoveryEntry {
    private final EntityElementDiscoverySource source;
    private final Player player;
    private final Entity entity;

    public EntityElementDiscoveryEntry(EntityElementDiscoverySource source, Player player, Entity entity) {
        this.source = source;
        this.player = player;
        this.entity = entity;
    }

    @Override
    public @Nullable SelectableElement getElement() {
        if (player.isHidden(entity)) {
            return null;
        }
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
