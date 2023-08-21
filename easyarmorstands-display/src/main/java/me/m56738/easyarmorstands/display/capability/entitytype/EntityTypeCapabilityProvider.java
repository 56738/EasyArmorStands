package me.m56738.easyarmorstands.display.capability.entitytype;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

public class EntityTypeCapabilityProvider implements CapabilityProvider<EntityTypeCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class<?> type = Class.forName("org.bukkit.Translatable");
            return type.isAssignableFrom(EntityType.class);
        } catch (Throwable e) {
            return false;
        }
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
                return Component.translatable(type.getTranslationKey());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
