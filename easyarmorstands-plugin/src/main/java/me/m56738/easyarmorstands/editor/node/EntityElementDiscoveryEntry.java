package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.capability.visibility.VisibilityCapability;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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
    public SelectableElement getElement() {
        VisibilityCapability visibilityCapability = EasyArmorStandsPlugin.getInstance()
                .getCapability(VisibilityCapability.class);
        if (visibilityCapability != null && !visibilityCapability.isNotHidden(player, entity)) {
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
        if (o == null || getClass() != o.getClass()) return false;
        EntityElementDiscoveryEntry that = (EntityElementDiscoveryEntry) o;
        return Objects.equals(source, that.source) && Objects.equals(player, that.player) && Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, player, entity);
    }
}
