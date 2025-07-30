package me.m56738.easyarmorstands.common.history.action;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.history.EntityReplacementListener;
import net.kyori.adventure.text.Component;

public interface Action extends EntityReplacementListener {
    boolean execute(ChangeContext context);

    boolean undo(ChangeContext context);

    Component describe();
}
