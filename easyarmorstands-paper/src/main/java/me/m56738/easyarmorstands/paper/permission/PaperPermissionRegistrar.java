package me.m56738.easyarmorstands.paper.permission;

import me.m56738.easyarmorstands.common.permission.Child;
import me.m56738.easyarmorstands.common.permission.Children;
import me.m56738.easyarmorstands.common.permission.Description;
import me.m56738.easyarmorstands.common.permission.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.permissions.Permission;
import org.intellij.lang.annotations.MagicConstant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaperPermissionRegistrar {
    private static final Map<String, Permission> registeredPermissions = new HashMap<>();

    public static void registerAll() {
        try {
            for (Field field : Permissions.class.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                    register(field);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        for (EntityType entityType : EntityType.values()) {
            register(entityType, Permissions.SPAWN, "spawning");
            register(entityType, Permissions.DESTROY, "destroying");
            register(entityType, Permissions.EDIT, "editing");
        }
    }

    public static String entityType(
            @MagicConstant(valuesFromClass = Permissions.class) String prefix,
            EntityType type) {
        return prefix + "." + type.name().toLowerCase(Locale.ROOT).replace("_", "");
    }

    private static void register(
            EntityType type,
            @MagicConstant(valuesFromClass = Permissions.class) String owner,
            String verb) {
        String name = entityType(owner, type);
        Map<String, Boolean> children = new HashMap<>(1);
        children.put(owner, true);
        register(new Permission(
                name,
                "Allow " + verb + " entities of type " + type,
                children));
    }

    public static Permission register(Permission permission) {
        try {
            Bukkit.getPluginManager().addPermission(permission);
        } catch (IllegalArgumentException e) {
            return null;
        }
        registeredPermissions.put(permission.getName(), permission);
        return permission;
    }

    public static void unregisterAll() {
        for (Permission permission : registeredPermissions.values()) {
            Bukkit.getPluginManager().removePermission(permission);
        }
        registeredPermissions.clear();
    }

    public static void unregister(Permission permission) {
        registeredPermissions.remove(permission.getName(), permission);
        Bukkit.getPluginManager().removePermission(permission);
    }

    private static void register(Field field) throws ReflectiveOperationException {
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

        register(new Permission(name, description, children));
    }
}
