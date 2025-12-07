package me.m56738.easyarmorstands.common.permission;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.KeyedPropertyType;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import org.intellij.lang.annotations.MagicConstant;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class CommonPermissions {
    public static Set<Permission> createPermissions(Iterable<String> entityTypes) {
        Set<Permission> permissions = new TreeSet<>(Comparator.comparing(Permission::name));
        try {
            for (Field field : Permissions.class.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                    permissions.add(create(field));
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        for (String entityType : entityTypes) {
            permissions.add(create(entityType, Permissions.SPAWN, "spawning"));
            permissions.add(create(entityType, Permissions.DESTROY, "destroying"));
            permissions.add(create(entityType, Permissions.EDIT, "editing"));
        }
        permissions.addAll(createAll(EntityPropertyTypes.class));
        permissions.addAll(createAll(ArmorStandPropertyTypes.class));
        permissions.addAll(createAll(DisplayPropertyTypes.class));
        permissions.addAll(createAll(BlockDisplayPropertyTypes.class));
        permissions.addAll(createAll(ItemDisplayPropertyTypes.class));
        permissions.addAll(createAll(TextDisplayPropertyTypes.class));
        permissions.addAll(createAll(InteractionPropertyTypes.class));
        permissions.addAll(createAll(MannequinPropertyTypes.class));
        return permissions;
    }

    public static String entityType(
            @MagicConstant(valuesFromClass = Permissions.class) String prefix,
            String type) {
        return prefix + "." + type.toLowerCase(Locale.ROOT).replace("_", "");
    }

    private static List<Permission> createAll(Class<?> typeHolder) {
        return Arrays.stream(typeHolder.getDeclaredFields())
                .filter(field -> {
                    int modifiers = field.getModifiers();
                    return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
                })
                .flatMap(field -> {
                    Object value;
                    try {
                        value = field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    if (value instanceof PropertyType<?> propertyType) {
                        return Stream.ofNullable(create(propertyType));
                    } else if (value instanceof KeyedPropertyType<?, ?> keyedPropertyType) {
                        return keyedPropertyType.getAll().stream()
                                .flatMap(type -> Stream.ofNullable(create(type)));
                    } else {
                        return Stream.of();
                    }
                })
                .toList();
    }

    private static @Nullable Permission create(PropertyType<?> type) {
        String permission = type.permission();
        if (permission != null) {
            return new Permission(permission, null, Map.of());
        } else {
            return null;
        }
    }

    private static Permission create(
            String type,
            @MagicConstant(valuesFromClass = Permissions.class) String owner,
            String verb) {
        String name = entityType(owner, type);
        Map<String, Boolean> children = new HashMap<>(1);
        children.put(owner, true);
        return new Permission(
                name,
                "Allow " + verb + " entities of type " + type,
                children);
    }

    private static Permission create(Field field) throws ReflectiveOperationException {
        String name = (String) field.get(null);

        Description descriptionAnnotation = field.getDeclaredAnnotation(Description.class);
        String description = descriptionAnnotation != null ? descriptionAnnotation.value() : null;

        Children childrenAnnotation = field.getDeclaredAnnotation(Children.class);
        Map<String, Boolean> children = new HashMap<>();
        if (childrenAnnotation != null) {
            for (Child child : childrenAnnotation.value()) {
                children.put(child.value(), true);
            }
        }

        return new Permission(name, description, children);
    }
}
