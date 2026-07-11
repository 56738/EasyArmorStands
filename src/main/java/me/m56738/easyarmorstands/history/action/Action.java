package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.api.history.EntityReplacementListener;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import net.kyori.adventure.text.Component;

public interface Action extends EntityReplacementListener {
    boolean execute(EasPlayer player);

    boolean undo(EasPlayer player);

    Component describe();
}
