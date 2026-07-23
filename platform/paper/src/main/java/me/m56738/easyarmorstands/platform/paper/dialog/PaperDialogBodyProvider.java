package me.m56738.easyarmorstands.platform.paper.dialog;

import io.papermc.paper.registry.data.dialog.body.DialogBody;
import me.m56738.easyarmorstands.platform.dialog.DialogBodyProvider;
import net.kyori.adventure.text.Component;

@SuppressWarnings("UnstableApiUsage")
public class PaperDialogBodyProvider implements DialogBodyProvider {
    @Override
    public PaperDialogBody createText(Component text) {
        return PaperDialogBody.fromNative(DialogBody.plainMessage(text));
    }
}
