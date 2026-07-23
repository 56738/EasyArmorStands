package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.platform.entity.Entity;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SimpleEntityElementProvider<E extends Entity> implements EntityElementProvider {
    private final EasyArmorStandsCommon eas;
    private final EntityElementType<E> type;

    public SimpleEntityElementProvider(EasyArmorStandsCommon eas, EntityElementType<E> type) {
        this.eas = eas;
        this.type = type;
    }

    @Override
    public Key key() {
        return type.key();
    }

    @Override
    public boolean canDetect(Entity entity) {
        if (!eas.getConfiguration().editor.allowEntities) {
            return false;
        }
        return type.getEntityType().equals(entity.type());
    }

    @Override
    public @Nullable Element getElement(Entity entity) {
        if (type.getEntityType().equals(entity.type())) {
            return type.getElement(type.getEntityClass().cast(entity));
        }
        return null;
    }
}
