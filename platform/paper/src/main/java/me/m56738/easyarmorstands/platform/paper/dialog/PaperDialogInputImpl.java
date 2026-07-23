package me.m56738.easyarmorstands.platform.paper.dialog;

import io.papermc.paper.registry.data.dialog.input.DialogInput;

@SuppressWarnings("UnstableApiUsage")
record PaperDialogInputImpl(DialogInput input) implements PaperDialogInput {
    @Override
    public DialogInput getNative() {
        return input;
    }
}
