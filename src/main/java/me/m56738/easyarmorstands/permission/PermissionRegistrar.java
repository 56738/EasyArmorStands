package me.m56738.easyarmorstands.permission;

import java.util.Map;

public interface PermissionRegistrar {
    void registerPermission(String name, String description, Map<String, Boolean> children);
}
