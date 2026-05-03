package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.api.history.EntityReplacementListener;
import me.m56738.easyarmorstands.context.ChangeContext;
import net.kyori.adventure.text.Component;

public interface Action extends EntityReplacementListener {
    boolean execute(ChangeContext context);

    boolean undo(ChangeContext context);

    Component describe();
}
