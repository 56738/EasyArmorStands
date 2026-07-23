package me.m56738.easyarmorstands.platform.paper.dialog;

import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import me.m56738.easyarmorstands.platform.dialog.DialogInputProvider;
import net.kyori.adventure.text.Component;

@SuppressWarnings("UnstableApiUsage")
public class PaperDialogInputProvider implements DialogInputProvider {
    @Override
    public PaperDialogInput createBoolean(String key, Component label, boolean initial) {
        return PaperDialogInput.fromNative(DialogInput.bool(key, label)
                .initial(initial)
                .build());
    }

    @Override
    public PaperDialogInput createText(String key, Component label, String initial) {
        return PaperDialogInput.fromNative(DialogInput.text(key, label)
                .multiline(TextDialogInput.MultilineOptions.create(null, 64))
                .initial(initial)
                .maxLength(32768)
                .build());
    }
}
