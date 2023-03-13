package me.m56738.easyarmorstands.history;

import java.util.ArrayDeque;
import java.util.UUID;

public class History {
    private final ArrayDeque<HistoryAction> past = new ArrayDeque<>();
    private final ArrayDeque<HistoryAction> future = new ArrayDeque<>();

    public void push(HistoryAction action) {
        past.push(action);
        future.clear();
        while (past.size() > 100) {
            past.removeLast();
        }
    }

    public HistoryAction undo() {
        HistoryAction action = past.poll();
        if (action != null) {
            future.push(action);
            action.undo();
        }
        return action;
    }

    public HistoryAction redo() {
        HistoryAction action = future.poll();
        if (action != null) {
            past.push(action);
            action.redo();
        }
        return action;
    }

    public void onEntityReplaced(UUID oldId, UUID newId) {
        for (HistoryAction action : past) {
            action.onEntityReplaced(oldId, newId);
        }
        for (HistoryAction action : future) {
            action.onEntityReplaced(oldId, newId);
        }
    }
}
