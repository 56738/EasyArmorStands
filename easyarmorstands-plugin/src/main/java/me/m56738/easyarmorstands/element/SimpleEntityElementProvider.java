package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SimpleEntityElementProvider<E extends Entity> implements EntityElementProvider {
    private final EntityElementType<E> type;

    public SimpleEntityElementProvider(EntityElementType<E> type) {
        this.type = type;
    }

    @Override
    public Key key() {
        return type.key();
    }

    @Override
    public boolean isApplicable(Entity entity) {
        if (!EasyArmorStandsPlugin.getInstance().getConfiguration().editor.allowEntities) {
            return false;
        }
        return type.getEntityClass().isInstance(entity);
    }

    @Override
    public @Nullable Element getElement(Entity entity) {
        if (type.getEntityClass().isInstance(entity)) {
            return type.getElement(type.getEntityClass().cast(entity));
        }
        return null;
    }
}
