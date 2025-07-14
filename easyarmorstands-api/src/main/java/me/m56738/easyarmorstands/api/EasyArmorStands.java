package me.m56738.easyarmorstands.api;

import me.m56738.easyarmorstands.api.context.ChangeContextFactory;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface EasyArmorStands {
    static @NotNull EasyArmorStands get() {
        if (EasyArmorStandsHolder.instance == null) {
            throw new IllegalStateException("EasyArmorStands not initialized");
        }
        return EasyArmorStandsHolder.instance;
    }

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
    @NotNull ChangeContextFactory changeContext();
}
