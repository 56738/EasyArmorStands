package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.event.EntityObjectInitializeEvent;
import me.m56738.easyarmorstands.property.entity.DefaultEntityProperties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SimpleEntityObjectProvider implements EntityObjectProvider {
    @Override
    public EntityObject createObject(Entity entity) {
        if (entity.getType() == EntityType.PLAYER && !entity.hasMetadata("NPC")) {
            return null;
        }
        SimpleEntityObject entityObject = new SimpleEntityObject(entity);
        DefaultEntityProperties.registerProperties(entityObject);
        Bukkit.getPluginManager().callEvent(new EntityObjectInitializeEvent(entityObject));
        return entityObject;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOWEST;
    }
}
