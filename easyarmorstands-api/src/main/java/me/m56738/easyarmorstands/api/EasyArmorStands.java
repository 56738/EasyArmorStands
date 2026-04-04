package me.m56738.easyarmorstands.api;

import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.ElementTypeRegistry;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.api.element.EntityElementReference;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeManager;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EasyArmorStands {
    String NAMESPACE = "easyarmorstands";

    static @NotNull EasyArmorStands get() {
        if (EasyArmorStandsHolder.instance == null) {
            throw new IllegalStateException("EasyArmorStands not initialized");
        }
        return EasyArmorStandsHolder.instance;
    }

    static @NotNull NamespacedKey key(@KeyPattern.Value String value) {
        return new NamespacedKey(NAMESPACE, value);
    }

    @Contract(pure = true)
    @NotNull ElementTypeRegistry elementTypeRegistry();

    @Contract(pure = true)
    @NotNull PropertyTypeRegistry propertyTypeRegistry();

    @Contract(pure = true)
    @NotNull EntityElementProviderRegistry entityElementProviderRegistry();

    @Contract(pure = true)
    @NotNull SessionManager sessionManager();

    @Contract(pure = true)
    @NotNull ElementSpawnRequest elementSpawnRequest(ElementType type);

    @Contract(pure = true)
    @NotNull RegionPrivilegeManager regionPrivilegeManager();

    @Contract(pure = true)
    @NotNull <E extends Entity> EntityElementReference<E> createReference(EntityElementType<E> type, E entity);

    /**
     * Attempts to resolve the entity into an element.
     * <p>
     * If an element provider is {@link #setEntityElementProvider stored inside the entity}, it will be used.
     * <p>
     * Otherwise, {@link EntityElementProvider#isApplicable} will be used to determine the correct provider.
     *
     * @param entity the entity to resolve
     * @return the element or {@code null}
     */
    @Nullable Element getElement(Entity entity);

    /**
     * Stores the key of the entity element provider inside the entity.
     * <p>
     * The provider will automatically be used when {@link #getElement resolving the entity into an element}.
     *
     * @param entity   the entity
     * @param provider the provider
     */
    void setEntityElementProvider(Entity entity, @Nullable EntityElementProvider provider);

    void refreshEntity(Entity entity);
}
