package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SimpleEntityElementProvider implements EntityElementProvider {
    @Override
    public Element getElement(Entity entity) {
        if (entity instanceof Player && !entity.hasMetadata("NPC")) {
            return null;
        }
        return new SimpleEntityElementType<>(entity.getType(), Util.getEntityClass(entity)).getElement(entity);
    }

    @Override
    public Priority getPriority() {
        return Priority.LOWEST;
    }
}
