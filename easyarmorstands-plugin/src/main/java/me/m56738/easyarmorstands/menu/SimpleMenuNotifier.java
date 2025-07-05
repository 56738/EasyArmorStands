package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.menu.MenuNotifier;

import java.util.ArrayList;
import java.util.List;

public class SimpleMenuNotifier implements MenuNotifier {
    private final List<Runnable> submitActions = new ArrayList<>();
    private final List<Runnable> commitActions = new ArrayList<>();

    @Override
    public void addSubmitAction(Runnable action) {
        submitActions.add(action);
    }

    @Override
    public void addCommitAction(Runnable action) {
        commitActions.add(action);
    }

    public void submit() {
        for (Runnable submitAction : submitActions) {
            submitAction.run();
        }
        for (Runnable commitAction : commitActions) {
            commitAction.run();
        }
    }
}
