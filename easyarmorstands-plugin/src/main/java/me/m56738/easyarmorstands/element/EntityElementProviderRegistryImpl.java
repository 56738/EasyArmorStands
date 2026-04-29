package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.element.EntityElementProvider.Priority;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntityElementProviderRegistryImpl implements EntityElementProviderRegistry {
    private final Map<Key, EntityElementProvider> providers = new HashMap<>();
    private final Map<Priority, List<EntityElementProvider>> providersByPriority = new LinkedHashMap<>();

    public EntityElementProviderRegistryImpl() {
        for (Priority priority : Priority.values()) {
            providersByPriority.put(priority, new ArrayList<>());
        }
    }

    @Override
    public void register(@NotNull EntityElementProvider provider) {
        providers.put(provider.key(), provider);
        providersByPriority.get(provider.getPriority()).add(provider);
    }

    public Element getElement(Entity entity) {
        Element element = getElementFromReferencedProvider(entity);
        if (element != null) {
            return element;
        }
        for (List<EntityElementProvider> providers : providersByPriority.values()) {
            for (EntityElementProvider provider : providers) {
                if (provider.canDetect(entity)) {
                    element = provider.getElement(entity);
                    if (element != null) {
                        return element;
                    }
                }
            }
        }
        return null;
    }

    public @Nullable EntityElementProvider registerEntity(Entity entity) {
        if (getReferencedProvider(entity) != null) {
            return null;
        }
        for (List<EntityElementProvider> providers : providersByPriority.values()) {
            for (EntityElementProvider provider : providers) {
                if (provider.getElement(entity) != null) {
                    EasyArmorStandsPlugin.getInstance().setEntityElementProvider(entity, provider);
                    return provider;
                }
            }
        }
        return null;
    }

    private @Nullable Element getElementFromReferencedProvider(Entity entity) {
        EntityElementProvider provider = getReferencedProvider(entity);
        if (provider == null) {
            return null;
        }
        return provider.getElement(entity);
    }

    private @Nullable EntityElementProvider getReferencedProvider(Entity entity) {
        Key providerKey = getReferencedProviderKey(entity);
        if (providerKey == null) {
            return null;
        }
        return providers.get(providerKey);
    }

    @SuppressWarnings("PatternValidation")
    private @Nullable Key getReferencedProviderKey(Entity entity) {
        String providerName = entity.getPersistentDataContainer().get(EntityElementKeys.ELEMENT_TYPE, PersistentDataType.STRING);
        if (providerName == null) {
            return null;
        }
        try {
            return Key.key(providerName);
        } catch (InvalidKeyException e) {
            return null;
        }
    }
}
