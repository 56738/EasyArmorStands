package me.m56738.easyarmorstands.group.property;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GroupPropertyContainer implements PropertyContainer {
    private final List<PropertyContainer> containers;

    public GroupPropertyContainer(Collection<PropertyContainer> containers) {
        this.containers = new ArrayList<>(containers);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void forEach(@NotNull Consumer<Property<?>> consumer) {
        Map<PropertyType, GroupProperty> properties = new LinkedHashMap<>();
        for (PropertyContainer container : containers) {
            container.forEach(property ->
                    properties.computeIfAbsent(property.getType(), GroupProperty::new)
                            .addProperty(property));
        }
        for (GroupProperty property : properties.values()) {
            consumer.accept(property);
        }
    }

    @Override
    public @Nullable <T> Property<T> getOrNull(@NotNull PropertyType<T> type) {
        GroupProperty<T> property = new GroupProperty<>(type);
        for (PropertyContainer container : containers) {
            Property<T> member = container.getOrNull(type);
            if (member != null) {
                property.addProperty(member);
            }
        }
        if (property.isEmpty()) {
            return null;
        }
        return property;
    }
}
