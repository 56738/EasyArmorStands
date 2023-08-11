package me.m56738.easyarmorstands.editor;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntityObjectProviderRegistry {
    private final Map<EntityObjectProvider.Priority, List<EntityObjectProvider>> providers = new LinkedHashMap<>();

    public EntityObjectProviderRegistry() {
        for (EntityObjectProvider.Priority priority : EntityObjectProvider.Priority.values()) {
            providers.put(priority, new ArrayList<>());
        }
    }

    public void register(EntityObjectProvider provider) {
        providers.get(provider.getPriority()).add(provider);
    }

    public @Nullable EditableObject createEditableObject(Entity entity) {
        if (!entity.isValid()) {
            return null;
        }
        if (entity.hasMetadata("easyarmorstands_ignore")) {
            return null;
        }
        for (MetadataValue metadataValue : entity.getMetadata("easyarmorstands_object")) {
            Object value = metadataValue.value();
            if (value instanceof EditableObject) {
                return (EditableObject) value;
            }
        }
        for (List<EntityObjectProvider> providers : providers.values()) {
            for (EntityObjectProvider provider : providers) {
                EntityObject entityObject = provider.createObject(entity);
                if (entityObject != null) {
                    return entityObject;
                }
            }
        }
        return null;
    }
}
