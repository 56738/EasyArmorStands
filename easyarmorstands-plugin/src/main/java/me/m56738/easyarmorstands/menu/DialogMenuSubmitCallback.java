package me.m56738.easyarmorstands.menu;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback;
import net.kyori.adventure.audience.Audience;

import java.util.List;

public class DialogMenuSubmitCallback implements DialogActionCallback {
    private final Runnable action;
    private final List<DialogMenuInput> inputs;

    public DialogMenuSubmitCallback(Runnable action, List<DialogMenuInput> inputs) {
        this.action = action;
        this.inputs = inputs;
    }

    @Override
    public void accept(DialogResponseView response, Audience audience) {
        for (DialogMenuInput input : inputs) {
            input.processResponse(response);
        }
        action.run();
    }
}
