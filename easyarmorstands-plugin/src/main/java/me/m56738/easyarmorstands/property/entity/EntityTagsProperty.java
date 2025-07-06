package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class EntityTagsProperty implements Property<Set<String>> {
    private final Entity entity;

    public EntityTagsProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Set<String>> getType() {
        return EntityPropertyTypes.TAGS;
    }

    @Override
    public @NotNull Set<String> getValue() {
        return entity.getScoreboardTags();
    }

    @Override
    public boolean setValue(@NotNull Set<String> value) {
        Set<String> currentTags = new HashSet<>(entity.getScoreboardTags());
        boolean changed = false;

        // handle removed tags
        for (String tag : currentTags) {
            if (!value.contains(tag)) {
                if (entity.removeScoreboardTag(tag)) {
                    changed = true;
                }
            }
        }

        // handle added tags
        for (String tag : value) {
            if (!currentTags.contains(tag)) {
                if (entity.addScoreboardTag(tag)) {
                    changed = true;
                }
            }
        }

        return changed;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
