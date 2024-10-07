package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.entitytag.EntityTagCapability;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class EntityTagsProperty implements Property<Set<String>> {
    private final Entity entity;
    private final EntityTagCapability tagCapability;

    public EntityTagsProperty(Entity entity, EntityTagCapability tagCapability) {
        this.entity = entity;
        this.tagCapability = tagCapability;
    }

    @Override
    public @NotNull PropertyType<Set<String>> getType() {
        return EntityPropertyTypes.TAGS;
    }

    @Override
    public @NotNull Set<String> getValue() {
        return tagCapability.getTags(entity);
    }

    @Override
    public boolean setValue(@NotNull Set<String> value) {
        Set<String> currentTags = new HashSet<>(tagCapability.getTags(entity));
        boolean changed = false;

        // handle removed tags
        for (String tag : currentTags) {
            if (!value.contains(tag)) {
                if (tagCapability.removeTag(entity, tag)) {
                    changed = true;
                }
            }
        }

        // handle added tags
        for (String tag : value) {
            if (!currentTags.contains(tag)) {
                if (tagCapability.addTag(entity, tag)) {
                    changed = true;
                }
            }
        }

        return changed;
    }
}
