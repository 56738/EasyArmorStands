package me.m56738.easyarmorstands.history;

import me.m56738.easyarmorstands.history.action.Action;

import java.util.ArrayDeque;
import java.util.UUID;

public class History {
    private final ArrayDeque<Action> past = new ArrayDeque<>();
    private final ArrayDeque<Action> future = new ArrayDeque<>();

    public ArrayDeque<Action> getPast() {
        return past;
    }

    public ArrayDeque<Action> getFuture() {
        return future;
    }

    public void push(Action action) {
        past.push(action);
        future.clear();
        while (past.size() > 100) {
            past.removeLast();
        }
    }

    public Action takeUndoAction() {
        Action action = past.poll();
        if (action != null) {
            future.push(action);
        }
        return action;
    }

    public Action takeRedoAction() {
        Action action = future.poll();
        if (action != null) {
            past.push(action);
        }
        return action;
    }

    public void onEntityReplaced(UUID oldId, UUID newId) {
        for (Action action : past) {
            action.onEntityReplaced(oldId, newId);
        }
        for (Action action : future) {
            action.onEntityReplaced(oldId, newId);
        }
    }
}
