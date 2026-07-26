package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogInputProvider;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.kyori.adventure.text.Component;
import net.minecraft.server.dialog.Input;
import net.minecraft.server.dialog.body.PlainMessage;
import net.minecraft.server.dialog.input.BooleanInput;
import net.minecraft.server.dialog.input.TextInput;

import java.util.Optional;

public class ModdedDialogInputProvider implements DialogInputProvider {
    private final ModdedPlatform platform;

    public ModdedDialogInputProvider(ModdedPlatform platform) {
        this.platform = platform;
    }

    @Override
    public ModdedDialogInput createBoolean(String key, Component label, boolean initial) {
        net.minecraft.network.chat.Component nativeLabel = platform.getAdventure().asNative(label);
        BooleanInput input = new BooleanInput(nativeLabel, initial, "true", "false");
        return ModdedDialogInput.fromNative(platform, new Input(key, input));
    }

    @Override
    public ModdedDialogInput createText(String key, Component label, String initial) {
        net.minecraft.network.chat.Component nativeLabel = platform.getAdventure().asNative(label);
        TextInput.MultilineOptions options = new TextInput.MultilineOptions(Optional.empty(), Optional.of(64));
        TextInput input = new TextInput(PlainMessage.DEFAULT_WIDTH, nativeLabel, true, initial, 32768, Optional.of(options));
        return ModdedDialogInput.fromNative(platform, new Input(key, input));
    }
}
