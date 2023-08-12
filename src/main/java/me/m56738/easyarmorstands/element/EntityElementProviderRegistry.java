package me.m56738.easyarmorstands.element;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntityElementProviderRegistry {
    private final Map<EntityElementProvider.Priority, List<EntityElementProvider>> providers = new LinkedHashMap<>();

    public EntityElementProviderRegistry() {
        for (EntityElementProvider.Priority priority : EntityElementProvider.Priority.values()) {
            providers.put(priority, new ArrayList<>());
        }
    }

    public void register(EntityElementProvider provider) {
        providers.get(provider.getPriority()).add(provider);
    }

    public Element getElement(Entity entity) {
        if (!entity.isValid()) {
            return null;
        }
        if (entity.hasMetadata("easyarmorstands_ignore")) {
            return null;
        }
        for (MetadataValue metadataValue : entity.getMetadata("easyarmorstands_element")) {
            Object value = metadataValue.value();
            if (value instanceof Element) {
                return (Element) value;
            }
        }
        for (List<EntityElementProvider> providers : providers.values()) {
            for (EntityElementProvider provider : providers) {
                Element element = provider.getElement(entity);
                if (element != null) {
                    return element;
                }
            }
        }
        return null;
    }
}
