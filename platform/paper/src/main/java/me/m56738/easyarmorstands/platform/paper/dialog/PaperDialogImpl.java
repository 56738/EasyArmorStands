package me.m56738.easyarmorstands.platform.paper.dialog;

import io.papermc.paper.dialog.Dialog;

record PaperDialogImpl(Dialog dialog) implements PaperDialog {
    @Override
    public Dialog getNative() {
        return dialog;
    }
}
