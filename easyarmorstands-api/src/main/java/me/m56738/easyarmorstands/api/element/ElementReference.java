package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.history.EntityReplacementListener;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface ElementReference extends EntityReplacementListener {
    ElementType getType();

    Element getElement();

    @Override
    default void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
    }
}
