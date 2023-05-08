package me.m56738.easyarmorstands.capability.entitytype.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

public class EntityTypeCapabilityProvider implements CapabilityProvider<EntityTypeCapability> {
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public EntityTypeCapability create(Plugin plugin) {
        return new EntityTypeCapabilityImpl();
    }

    private static class EntityTypeCapabilityImpl implements EntityTypeCapability {
        @Override
        public Component getName(EntityType type) {
            try {
                return Component.text(type.getEntityClass().getSimpleName());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
