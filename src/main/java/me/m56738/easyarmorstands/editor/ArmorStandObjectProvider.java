package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.event.EntityObjectInitializeEvent;
import me.m56738.easyarmorstands.node.EntityObjectProvider;
import me.m56738.easyarmorstands.property.entity.DefaultEntityProperties;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class ArmorStandObjectProvider implements EntityObjectProvider {
    private final Session session;

    public ArmorStandObjectProvider(Session session) {
        this.session = session;
    }

    @Override
    public EntityObject createObject(Entity entity) {
        if (entity instanceof ArmorStand) {
            ArmorStandObject armorStandObject = new ArmorStandObject(session, (ArmorStand) entity);
            DefaultEntityProperties.registerProperties(armorStandObject);
            Bukkit.getPluginManager().callEvent(new EntityObjectInitializeEvent(armorStandObject));
            return armorStandObject;
        }
        return null;
    }
}
