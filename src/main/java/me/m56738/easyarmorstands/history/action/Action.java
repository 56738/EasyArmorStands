package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.history.EntityReplacementListener;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface Action extends EntityReplacementListener {
    boolean execute(ChangeContext context);

    boolean undo(ChangeContext context);

    Component describe();
}
