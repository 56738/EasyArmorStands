package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.command.sender.EasPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class GroupAction implements Action {
    private final Action[] actions;
    private Component description;

    public GroupAction(List<? extends Action> actions, @Nullable Component description) {
        this.description = description;
        this.actions = actions.toArray(new Action[0]);
    }

    @Override
    public boolean execute(EasPlayer player) {
        boolean ok = true;
        for (Action action : actions) {
            ok &= action.execute(player);
        }
        return ok;
    }

    @Override
    public boolean undo(EasPlayer player) {
        boolean ok = true;
        for (int i = actions.length - 1; i >= 0; i--) {
            ok &= actions[i].undo(player);
        }
        return ok;
    }

    @Override
    public Component describe() {
        if (description == null) {
            TextComponent.Builder builder = Component.text();
            for (int i = 0; i < actions.length; i++) {
                if (i != 0) {
                    builder.append(Component.text(", ", NamedTextColor.DARK_GRAY));
                }
                builder.append(actions[i].describe());
            }
            description = builder.build();
        }
        return description;
    }

    @Override
    public void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
        for (Action action : actions) {
            action.onEntityReplaced(oldId, newId);
        }
    }
}
