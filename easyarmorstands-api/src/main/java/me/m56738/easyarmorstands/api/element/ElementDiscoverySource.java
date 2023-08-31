package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ElementDiscoverySource {
    void discover(@NotNull EyeRay eyeRay, @NotNull Consumer<@NotNull ElementDiscoveryEntry> consumer);
}
