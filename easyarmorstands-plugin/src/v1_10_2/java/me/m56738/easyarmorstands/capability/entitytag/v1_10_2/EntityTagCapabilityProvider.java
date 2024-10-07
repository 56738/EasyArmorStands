package me.m56738.easyarmorstands.capability.entitytag.v1_10_2;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.entitytag.EntityTagCapability;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class EntityTagCapabilityProvider implements CapabilityProvider<EntityTagCapability> {
    @Override
    public boolean isSupported() {
        try {
            Entity.class.getMethod("getScoreboardTags");
            Entity.class.getMethod("addScoreboardTag", String.class);
            Entity.class.getMethod("removeScoreboardTag", String.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public EntityTagCapability create(Plugin plugin) {
        return new EntityTagCapabilityImpl();
    }

    private static class EntityTagCapabilityImpl implements EntityTagCapability {
        @Override
        public Set<String> getTags(Entity entity) {
            return Collections.unmodifiableSet(entity.getScoreboardTags());
        }

        @Override
        public boolean addTag(Entity entity, String tag) {
            return entity.addScoreboardTag(tag);
        }

        @Override
        public boolean removeTag(Entity entity, String tag) {
            return entity.removeScoreboardTag(tag);
        }
    }
}
