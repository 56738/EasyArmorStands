package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.element.DestroyableElement;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.element.ElementReference;
import me.m56738.easyarmorstands.element.ElementType;
import me.m56738.easyarmorstands.element.EntityElementReference;
import me.m56738.easyarmorstands.event.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.event.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertySnapshot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

abstract class ElementPresenceAction implements Action {
    private final ElementType type;
    private final PropertyContainer properties;
    private ElementReference reference;

    public ElementPresenceAction(Element element) {
        this.type = element.getType();
        this.properties = new PropertySnapshot(element.getProperties());
        this.reference = element.getReference();
    }

    protected boolean create(Player player) {
        PlayerCreateElementEvent event = new PlayerCreateElementEvent(player, type, properties);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        Element element = type.createElement(properties);
        if (element == null) {
            return false;
        }

        UUID oldId = getId(reference);
        reference = element.getReference();
        UUID newId = getId(reference);
        if (oldId != null && newId != null) {
            EasyArmorStands.getInstance().getHistoryManager().onEntityReplaced(oldId, newId);
        }

        return true;
    }

    protected boolean destroy(Player player) {
        if (reference == null) {
            return false;
        }

        Element element = reference.getElement();
        if (!(element instanceof DestroyableElement)) {
            return false;
        }

        PlayerDestroyElementEvent event = new PlayerDestroyElementEvent(player, element);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        ((DestroyableElement) element).destroy();
        return true;
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (reference != null) {
            reference.onEntityReplaced(oldId, newId);
        }
    }

    private UUID getId(ElementReference reference) {
        if (reference instanceof EntityElementReference) {
            return ((EntityElementReference<?>) reference).getId();
        }
        return null;
    }

    public ElementType getType() {
        return type;
    }
}
