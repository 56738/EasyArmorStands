package me.m56738.easyarmorstands.menu;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback;
import net.kyori.adventure.audience.Audience;

public class DialogMenuActionCallback implements DialogActionCallback {
    private final Runnable action;

    public DialogMenuActionCallback(Runnable action) {
        this.action = action;
    }

    @Override
    public void accept(DialogResponseView response, Audience audience) {
        action.run();
    }
}
