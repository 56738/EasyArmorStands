package me.m56738.easyarmorstands.property;

import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EntityPropertyTypeRegistry {
    private final Set<EntityPropertyType<?>> types = new HashSet<>();
    private final Set<EntityPropertyType<?>> view = Collections.unmodifiableSet(types);

    public void register(EntityPropertyType<?> type) {
        if (!types.add(type)) {
            throw new IllegalStateException("Duplicate entity property type");
        }
    }

    @UnmodifiableView
    public Set<EntityPropertyType<?>> getTypes() {
        return view;
    }
}
