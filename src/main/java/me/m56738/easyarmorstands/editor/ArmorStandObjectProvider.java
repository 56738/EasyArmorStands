package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.event.EntityObjectInitializeEvent;
import me.m56738.easyarmorstands.property.entity.DefaultEntityProperties;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class ArmorStandObjectProvider implements EntityObjectProvider {
    @Override
    public EntityObject createObject(Entity entity) {
        if (entity instanceof ArmorStand) {
            ArmorStandObject armorStandObject = new ArmorStandObject((ArmorStand) entity);
            DefaultEntityProperties.registerProperties(armorStandObject);
            Bukkit.getPluginManager().callEvent(new EntityObjectInitializeEvent(armorStandObject));
            return armorStandObject;
        }
        return null;
    }
}
