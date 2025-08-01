package me.m56738.easyarmorstands.common.permission;

import org.jspecify.annotations.Nullable;

import java.util.Map;

public record Permission(String name, @Nullable String description, Map<String, Boolean> children) {
}
