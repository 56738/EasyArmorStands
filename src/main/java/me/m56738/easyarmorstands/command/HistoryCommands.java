package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Range;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.action.Action;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Iterator;

@CommandMethod("eas")
public class HistoryCommands {
    @CommandMethod("history")
    @CommandPermission("easyarmorstands.history")
    @CommandDescription("View your history")
    public void history(EasPlayer sender) {
        History history = EasyArmorStands.getInstance().getHistory(sender.get());
        if (history.getPast().isEmpty()) {
            sender.sendMessage(Component.text("Your history is empty.", NamedTextColor.GRAY));
            return;
        }
        sender.sendMessage(Component.text("History:", NamedTextColor.GOLD));
        for (Iterator<Action> it = history.getPast().descendingIterator(); it.hasNext(); ) {
            Action action = it.next();
            sender.sendMessage(Component.text()
                    .content("* ")
                    .color(NamedTextColor.GRAY)
                    .append(action.describe()));
        }
    }

    @CommandMethod("redo [count]")
    @CommandPermission("easyarmorstands.redo")
    @CommandDescription("Redo a change")
    public void redo(EasPlayer sender,
                     @Range(min = "1", max = "10") @Argument(value = "count", defaultValue = "1") int count) {
        History history = EasyArmorStands.getInstance().getHistory(sender.get());
        for (int i = 0; i < count; i++) {
            Action action = history.takeRedoAction();
            if (action != null) {
                try {
                    action.execute();
                } catch (IllegalStateException e) {
                    sender.sendMessage(Component.text("Unable to redo change: ", NamedTextColor.RED)
                            .append(action.describe()));
                    break;
                }
                sender.sendMessage(Component.text()
                        .append(Component.text("Redone change: ", NamedTextColor.GREEN))
                        .append(action.describe()));
            } else {
                sender.sendMessage(Component.text("No changes left to redo", NamedTextColor.RED));
                break;
            }
        }
    }

    @CommandMethod("undo [count]")
    @CommandPermission("easyarmorstands.undo")
    @CommandDescription("Undo a change")
    public void undo(EasPlayer sender,
                     @Range(min = "1", max = "10") @Argument(value = "count", defaultValue = "1") int count) {
        History history = EasyArmorStands.getInstance().getHistory(sender.get());
        for (int i = 0; i < count; i++) {
            Action action = history.takeUndoAction();
            if (action != null) {
                try {
                    action.undo();
                } catch (IllegalStateException e) {
                    sender.sendMessage(Component.text("Unable to undo change: ", NamedTextColor.RED)
                            .append(action.describe()));
                    break;
                }
                sender.sendMessage(Component.text()
                        .append(Component.text("Undone change: ", NamedTextColor.GREEN))
                        .append(action.describe()));
            } else {
                sender.sendMessage(Component.text("No changes left to undo", NamedTextColor.RED));
                break;
            }
        }
    }
}
