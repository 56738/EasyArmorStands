package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogInput;
import me.m56738.easyarmorstands.platform.dialog.DialogInputProvider;
import net.kyori.adventure.text.Component;

public class ModdedDialogInputProvider implements DialogInputProvider {
    @Override
    public DialogInput createBoolean(String key, Component label, boolean initial) {
        return null;
    }

    @Override
    public DialogInput createText(String key, Component label, String initial) {
        return null;
    }
}
