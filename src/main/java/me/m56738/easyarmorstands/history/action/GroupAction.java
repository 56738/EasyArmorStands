package me.m56738.easyarmorstands.history.action;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class GroupAction implements Action {
    private final Action[] actions;

    public GroupAction(List<? extends Action> actions) {
        this.actions = actions.toArray(new Action[0]);
    }

    @Override
    public boolean execute(Player player) {
        boolean ok = true;
        for (Action action : actions) {
            ok &= action.execute(player);
        }
        return ok;
    }

    @Override
    public boolean undo(Player player) {
        boolean ok = true;
        for (int i = actions.length - 1; i >= 0; i--) {
            ok &= actions[i].undo(player);
        }
        return ok;
    }

    @Override
    public Component describe() {
        TextComponent.Builder builder = Component.text();
        for (int i = 0; i < actions.length; i++) {
            if (i != 0) {
                builder.append(Component.text(", ", NamedTextColor.DARK_GRAY));
            }
            builder.append(actions[i].describe());
        }
        return builder.build();
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        for (Action action : actions) {
            action.onEntityReplaced(oldId, newId);
        }
    }
}
