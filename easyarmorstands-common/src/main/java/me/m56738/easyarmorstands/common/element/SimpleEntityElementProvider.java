package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import org.jetbrains.annotations.NotNull;

public class SimpleEntityElementProvider implements EntityElementProvider {
    private final EasyArmorStandsCommon eas;

    public SimpleEntityElementProvider(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @Override
    public Element getElement(@NotNull Entity entity) {
        // TODO ignore players
        return new SimpleEntityElementType(eas, entity.getType()).getElement(entity);
    }

    @Override
    public @NotNull Priority getPriority() {
        return Priority.LOWEST;
    }
}
