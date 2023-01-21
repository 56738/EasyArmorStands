package gg.bundlegroup.easyarmorstands.platform.bukkit.feature;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Entity;

public interface EntityNameAccessor {
    Component getName(Entity entity);

    void setName(Entity entity, Component name);

    interface Provider extends FeatureProvider<EntityNameAccessor> {
    }

    class Fallback implements EntityNameAccessor, Provider {
        private final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

        @Override
        public Component getName(Entity entity) {
            return serializer.deserializeOrNull(entity.getCustomName());
        }

        @Override
        public void setName(Entity entity, Component name) {
            entity.setCustomName(serializer.serializeOrNull(name));
        }

        @Override
        public boolean isSupported() {
            return true;
        }

        @Override
        public Priority getPriority() {
            return Priority.FALLBACK;
        }

        @Override
        public EntityNameAccessor create() {
            return this;
        }
    }
}
