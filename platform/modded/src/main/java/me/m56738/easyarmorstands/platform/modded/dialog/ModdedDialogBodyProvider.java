package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogBodyProvider;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.kyori.adventure.text.Component;
import net.minecraft.server.dialog.body.PlainMessage;

public class ModdedDialogBodyProvider implements DialogBodyProvider {
    private final ModdedPlatform platform;

    public ModdedDialogBodyProvider(ModdedPlatform platform) {
        this.platform = platform;
    }

    @Override
    public ModdedDialogBody createText(Component text) {
        return ModdedDialogBody.fromNative(platform, new PlainMessage(platform.getAdventure().asNative(text), PlainMessage.DEFAULT_WIDTH));
    }
}
