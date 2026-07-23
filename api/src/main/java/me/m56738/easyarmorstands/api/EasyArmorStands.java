package me.m56738.easyarmorstands.api;

import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.ElementTypeRegistry;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.platform.entity.Entity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EasyArmorStands {
    String NAMESPACE = "easyarmorstands";

    static @NotNull Key key(@KeyPattern.Value String value) {
        return Key.key(NAMESPACE, value);
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

    /**
     * Attempts to resolve the entity into an element.
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
