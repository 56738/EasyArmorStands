package me.m56738.easyarmorstands.api;

import me.m56738.easyarmorstands.api.context.ChangeContextFactory;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.PlatformHolder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface EasyArmorStands extends PlatformHolder {
    String NAMESPACE = "easyarmorstands";

    @Contract(pure = true)
    @NotNull Platform getPlatform();

    @Contract(pure = true)
    @NotNull EntityElementProviderRegistry getEntityElementProviderRegistry();

    @Contract(pure = true)
    @NotNull SessionManager getSessionManager();

    @Contract(pure = true)
    @NotNull ElementSpawnRequest createElementSpawnRequest(ElementType type);

    @Contract(pure = true)
    @NotNull ChangeContextFactory getChangeContextFactory();

    @Contract(pure = true)
    @NotNull MenuBuilder createMenuBuilder();
}
