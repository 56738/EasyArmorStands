package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementReference;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.element.EntityElementReference;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntitySnapshot;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

abstract class ElementPresenceAction implements Action {
    private final EasyArmorStandsCommon eas;
    private final PropertyContainer properties;
    private final @Nullable EntitySnapshot snapshot;
    private ElementReference reference;

    public ElementPresenceAction(@NotNull EasyArmorStandsCommon eas, @NotNull Element element) {
        this.eas = eas;
        this.properties = new PropertyMap(element.getProperties());
        this.snapshot = getSnapshot(element);
        this.reference = element.getReference(eas.referenceProvider());
    }

    private static @Nullable EntitySnapshot getSnapshot(Element element) {
        if (element instanceof EntityElement<?> entityElement) {
            return entityElement.getEntity().createSnapshot();
        } else {
            return null;
        }
    }

    protected boolean create(EasPlayer player) {
        ElementType type = reference.getType();
        if (!player.canCreateElement(type, properties)) {
            return false;
        }

        Element element = null;
        if (type instanceof EntityElementType<?> entityElementType && player.get().hasPermission(Permissions.COPY_ENTITY)) {
            element = createEntity(entityElementType);
        }
        if (element == null) {
            element = type.createElement(properties);
        }
        if (element == null) {
            return false;
        }

        UUID oldId = getId(reference);
        reference = element.getReference(eas.referenceProvider());
        UUID newId = getId(reference);
        if (oldId != null && newId != null) {
            eas.getHistoryManager().onEntityReplaced(oldId, newId);
        }

        return true;
    }

    protected boolean destroy(EasPlayer player) {
        if (reference == null) {
            return false;
        }

        Element element = reference.getElement();
        if (!(element instanceof DestroyableElement destroyableElement)) {
            return false;
        }

        if (!player.canDestroyElement(destroyableElement)) {
            return false;
        }

        destroyableElement.destroy();
        return true;
    }

    private <E extends Entity> @Nullable Element createEntity(EntityElementType<E> type) {
        if (snapshot == null) {
            return null;
        }
        E entity = type.getEntityClass().cast(snapshot.createEntity(properties.get(EntityPropertyTypes.LOCATION).getValue()));
        return type.getElement(entity);
    }

    @Override
    public void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
        if (reference != null) {
            reference.onEntityReplaced(oldId, newId);
        }
    }

    private UUID getId(ElementReference reference) {
        if (reference instanceof EntityElementReference) {
            return ((EntityElementReference<?>) reference).getId();
        }
        return null;
    }

    public ElementType getType() {
        return reference.getType();
    }
}
