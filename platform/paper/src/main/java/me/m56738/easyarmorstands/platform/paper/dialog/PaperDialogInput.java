package me.m56738.easyarmorstands.platform.paper.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogInput;

@SuppressWarnings("UnstableApiUsage")
public interface PaperDialogInput extends DialogInput {
    static PaperDialogInput fromNative(io.papermc.paper.registry.data.dialog.input.DialogInput input) {
        return new PaperDialogInputImpl(input);
    }

    io.papermc.paper.registry.data.dialog.input.DialogInput getNative();

    static io.papermc.paper.registry.data.dialog.input.DialogInput toNative(DialogInput input) {
        return ((PaperDialogInput) input).getNative();
    }
}
