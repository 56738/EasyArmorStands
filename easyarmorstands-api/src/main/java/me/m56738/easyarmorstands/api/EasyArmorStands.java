package me.m56738.easyarmorstands.api;

import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.api.menu.MenuSlotTypeRegistry;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
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
    @NotNull MenuSlotTypeRegistry menuSlotTypeRegistry();

    @Contract(pure = true)
    @NotNull SessionManager sessionManager();
}
