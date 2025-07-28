package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class SimpleEntityElementProvider implements EntityElementProvider {
    private final Platform platform;

    public SimpleEntityElementProvider(Platform platform) {
        this.platform = platform;
    }

    @Override
    public Element getElement(@NotNull Entity entity) {
        // TODO ignore players
        return new SimpleEntityElementType(platform, entity.getType()).getElement(entity);
    }

    @Override
    public @NotNull Priority getPriority() {
        return Priority.LOWEST;
    }
}
