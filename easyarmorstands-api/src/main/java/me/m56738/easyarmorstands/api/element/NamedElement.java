package me.m56738.easyarmorstands.api.element;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface NamedElement extends Element {
    @NotNull Component getName();
}
