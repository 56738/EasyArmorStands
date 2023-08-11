package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.history.EntityReplacementListener;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface Action extends EntityReplacementListener {
    boolean execute(Player player);

    boolean undo(Player player);

    Component describe();
}
