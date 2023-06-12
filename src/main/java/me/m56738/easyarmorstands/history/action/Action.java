package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.property.ChangeContext;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public interface Action {
    boolean execute(ChangeContext context);

    boolean undo(ChangeContext context);

    Component describe();

    /**
     * When an armor stand is spawned, it has a random UUID.
     * For example, to undo a destroy action, we spawn a replacement armor stand.
     * All history actions must be updated to refer to this replacement.
     *
     * @param oldId The UUID of the entity which was replaced.
     * @param newId The UUID of the replacement.
     */
    void onEntityReplaced(UUID oldId, UUID newId);
}
