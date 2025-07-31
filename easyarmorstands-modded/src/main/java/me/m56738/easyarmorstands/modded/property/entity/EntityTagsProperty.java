package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.minecraft.world.entity.Entity;

import java.util.Set;

public class EntityTagsProperty extends EntityProperty<Entity, Set<String>> {
    public EntityTagsProperty(Entity entity) {
        super(entity);
    }

    @Override
    public PropertyType<Set<String>> getType() {
        return EntityPropertyTypes.TAGS;
    }

    @Override
    public Set<String> getValue() {
        return Set.copyOf(entity.getTags());
    }

    @Override
    public boolean setValue(Set<String> value) {
        for (String tag : value) {
            if (!entity.getTags().contains(tag)) {
                entity.addTag(tag);
            }
        }
        for (String tag : entity.getTags()) {
            if (!value.contains(tag)) {
                entity.removeTag(tag);
            }
        }
        return true;
    }
}
