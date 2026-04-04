package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.ElementTypeRegistry;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.TreeMap;

@NullMarked
public class ElementTypeRegistryImpl implements ElementTypeRegistry {
    private final Map<Key, ElementType> types = new TreeMap<>();

    @Override
    public void register(Key key, ElementType type) {
        types.put(key, type);
    }

    @Override
    public @Nullable ElementType getOrNull(Key key) {
        return types.get(key);
    }
}
