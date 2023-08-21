package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.history.EntityReplacementListener;

import java.util.UUID;

public interface ElementReference extends EntityReplacementListener {
    ElementType getType();

    Element getElement();

    @Override
    default void onEntityReplaced(UUID oldId, UUID newId) {
    }
}
