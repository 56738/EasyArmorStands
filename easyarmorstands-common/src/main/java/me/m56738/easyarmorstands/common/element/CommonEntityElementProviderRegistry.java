package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommonEntityElementProviderRegistry implements EntityElementProviderRegistry {
    private final CommonPlatform platform;
    private final Map<EntityElementProvider.Priority, List<EntityElementProvider>> providers = new LinkedHashMap<>();

    public CommonEntityElementProviderRegistry(CommonPlatform platform) {
        this.platform = platform;
        for (EntityElementProvider.Priority priority : EntityElementProvider.Priority.values()) {
            providers.put(priority, new ArrayList<>());
        }
    }

    @Override
    public void register(EntityElementProvider provider) {
        providers.get(provider.getPriority()).add(provider);
    }

    public @Nullable Element getElement(Entity entity) {
        if (!entity.isValid()) {
            return null;
        }
        if (platform.isIgnored(entity)) {
            return null;
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
