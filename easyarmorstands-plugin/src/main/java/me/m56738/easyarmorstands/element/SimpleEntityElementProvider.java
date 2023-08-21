package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SimpleEntityElementProvider implements EntityElementProvider {
    @Override
    public Element getElement(Entity entity) {
        if (entity instanceof Player && !entity.hasMetadata("NPC")) {
            return null;
        }
        Component name = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType());
        return new SimpleEntityElementType<>(Util.getEntityClass(entity), name).getElement(entity);
    }

    @Override
    public Priority getPriority() {
        return Priority.LOWEST;
    }
}
