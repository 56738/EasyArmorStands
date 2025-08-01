package me.m56738.easyarmorstands.common.permission;

import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import org.intellij.lang.annotations.MagicConstant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CommonPermissions {
    public static Set<Permission> createPermissions(Iterable<EntityType> entityTypes) {
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
        for (EntityType entityType : entityTypes) {
            permissions.add(create(entityType, Permissions.SPAWN, "spawning"));
            permissions.add(create(entityType, Permissions.DESTROY, "destroying"));
            permissions.add(create(entityType, Permissions.EDIT, "editing"));
        }
        return permissions;
    }

    public static String entityType(
            @MagicConstant(valuesFromClass = Permissions.class) String prefix,
            EntityType type) {
        return prefix + "." + type.name().toLowerCase(Locale.ROOT).replace("_", "");
    }

    private static Permission create(
            EntityType type,
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
