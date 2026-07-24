package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogInput;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import net.minecraft.server.dialog.Input;

public interface ModdedDialogInput extends DialogInput, ModdedPlatformHolder {
    Input getNative();

    static ModdedDialogInput fromNative(ModdedPlatform platform, Input input) {
        return new ModdedDialogInputImpl(platform, input);
    }

    static Input toNative(DialogInput input) {
        return ((ModdedDialogInput) input).getNative();
    }
}
