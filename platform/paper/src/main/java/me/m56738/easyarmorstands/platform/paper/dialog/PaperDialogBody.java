package me.m56738.easyarmorstands.platform.paper.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogBody;

@SuppressWarnings("UnstableApiUsage")
public interface PaperDialogBody extends DialogBody {
    static PaperDialogBody fromNative(io.papermc.paper.registry.data.dialog.body.DialogBody body) {
        return new PaperDialogBodyImpl(body);
    }

    io.papermc.paper.registry.data.dialog.body.DialogBody getNative();

    static io.papermc.paper.registry.data.dialog.body.DialogBody toNative(DialogBody body) {
        return ((PaperDialogBody) body).getNative();
    }
}
