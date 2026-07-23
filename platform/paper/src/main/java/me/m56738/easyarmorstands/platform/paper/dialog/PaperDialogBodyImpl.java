package me.m56738.easyarmorstands.platform.paper.dialog;

import io.papermc.paper.registry.data.dialog.body.DialogBody;

@SuppressWarnings("UnstableApiUsage")
record PaperDialogBodyImpl(DialogBody body) implements PaperDialogBody {
    @Override
    public DialogBody getNative() {
        return body;
    }
}
