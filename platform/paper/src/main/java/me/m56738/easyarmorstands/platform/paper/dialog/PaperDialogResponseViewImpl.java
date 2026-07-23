package me.m56738.easyarmorstands.platform.paper.dialog;

import io.papermc.paper.dialog.DialogResponseView;

@SuppressWarnings("UnstableApiUsage")
record PaperDialogResponseViewImpl(DialogResponseView view) implements PaperDialogResponseView {
    @Override
    public DialogResponseView getNative() {
        return view;
    }
}
