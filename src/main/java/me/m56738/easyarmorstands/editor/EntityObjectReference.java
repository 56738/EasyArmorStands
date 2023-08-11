package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.lookup.LookupCapability;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntityObjectReference implements EditableObjectReference {
    private UUID uuid;

    public EntityObjectReference(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public @Nullable EditableObject restoreObject() {
        Entity entity = EasyArmorStands.getInstance().getCapability(LookupCapability.class).getEntity(uuid);
        if (entity == null) {
            return null;
        }
        return EasyArmorStands.getInstance().getEntityObjectProviderRegistry().createEditableObject(entity);
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (uuid.equals(oldId)) {
            uuid = newId;
        }
    }
}
