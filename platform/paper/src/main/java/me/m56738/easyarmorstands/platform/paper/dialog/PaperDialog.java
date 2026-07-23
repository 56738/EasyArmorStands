package me.m56738.easyarmorstands.platform.paper.dialog;

import me.m56738.easyarmorstands.platform.dialog.Dialog;

public interface PaperDialog extends Dialog {
    static PaperDialog fromNative(io.papermc.paper.dialog.Dialog dialog) {
        return new PaperDialogImpl(dialog);
    }

    io.papermc.paper.dialog.Dialog getNative();

    static io.papermc.paper.dialog.Dialog toNative(Dialog dialog) {
        return ((PaperDialog) dialog).getNative();
    }
}
