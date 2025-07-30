package me.m56738.easyarmorstands.common.history;

import me.m56738.easyarmorstands.common.history.action.Action;
import me.m56738.easyarmorstands.common.history.action.GroupAction;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.List;
import java.util.UUID;

public class History {
    private final ArrayDeque<Action> past = new ArrayDeque<>();
    private final ArrayDeque<Action> future = new ArrayDeque<>();
    private final ChangeTracker tracker;

    public History(CommonPlatform platform) {
        this.tracker = new ChangeTracker(platform, this);
    }

    public ArrayDeque<Action> getPast() {
        return past;
    }

    public ArrayDeque<Action> getFuture() {
        return future;
    }

    public ChangeTracker getTracker() {
        return tracker;
    }

    public void push(Action action) {
        past.push(action);
        future.clear();
        while (past.size() > 100) {
            past.removeLast();
        }
    }

    public void push(List<? extends Action> actions, @Nullable Component description) {
        if (actions.size() == 1) {
            push(actions.get(0));
        } else if (actions.size() > 1) {
            push(new GroupAction(actions, description));
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

    public void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
        for (Action action : past) {
            action.onEntityReplaced(oldId, newId);
        }
        for (Action action : future) {
            action.onEntityReplaced(oldId, newId);
        }
    }
}
