package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Range;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Iterator;

@CommandMethod("eas")
public class HistoryCommands {
    @CommandMethod("history")
    @CommandPermission(Permissions.HISTORY)
    @CommandDescription("View your history")
    public void history(EasPlayer sender) {
        History history = sender.history();
        if (history.getPast().isEmpty()) {
            sender.sendMessage(Message.warning("easyarmorstands.warning.history-empty"));
            return;
        }
        sender.sendMessage(Message.title("easyarmorstands.title.history"));
        for (Iterator<Action> it = history.getPast().descendingIterator(); it.hasNext(); ) {
            Action action = it.next();
            sender.sendMessage(Component.text()
                    .content("* ")
                    .color(NamedTextColor.GRAY)
                    .append(action.describe()));
        }
    }

    @CommandMethod("redo [count]")
    @CommandPermission(Permissions.REDO)
    @CommandDescription("Redo a change")
    public void redo(EasPlayer sender,
                     @Range(min = "1", max = "10") @Argument(value = "count", defaultValue = "1") int count) {
        History history = sender.history();
        for (int i = 0; i < count; i++) {
            Action action = history.takeRedoAction();
            if (action != null) {
                if (!action.execute(sender)) {
                    sender.sendMessage(Message.error("easyarmorstands.error.cannot-redo", action.describe()));
                    break;
                }
                sender.sendMessage(Message.success("easyarmorstands.success.redone-change", action.describe()));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.nothing-to-redo"));
                break;
            }
        }
    }

    @CommandMethod("undo [count]")
    @CommandPermission(Permissions.UNDO)
    @CommandDescription("Undo a change")
    public void undo(EasPlayer sender,
                     @Range(min = "1", max = "10") @Argument(value = "count", defaultValue = "1") int count) {
        History history = sender.history();
        for (int i = 0; i < count; i++) {
            Action action = history.takeUndoAction();
            if (action != null) {
                if (!action.undo(sender)) {
                    sender.sendMessage(Message.error("easyarmorstands.error.cannot-undo", action.describe()));
                    break;
                }
                sender.sendMessage(Message.success("easyarmorstands.success.undone-change", action.describe()));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.nothing-to-undo"));
                break;
            }
        }
    }
}
