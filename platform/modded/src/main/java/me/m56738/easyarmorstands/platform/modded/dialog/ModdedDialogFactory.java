package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.dialog.Dialog;
import me.m56738.easyarmorstands.platform.dialog.DialogBody;
import me.m56738.easyarmorstands.platform.dialog.DialogFactory;
import me.m56738.easyarmorstands.platform.dialog.DialogInput;
import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.minecraft.resources.Identifier;
import net.minecraft.server.dialog.ActionButton;
import net.minecraft.server.dialog.CommonButtonData;
import net.minecraft.server.dialog.CommonDialogData;
import net.minecraft.server.dialog.ConfirmationDialog;
import net.minecraft.server.dialog.DialogAction;
import net.minecraft.server.dialog.action.CustomAll;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ModdedDialogFactory implements DialogFactory {
    private final ModdedPlatform platform;
    private final ModdedDialogBodyProvider bodyProvider = new ModdedDialogBodyProvider();
    private final ModdedDialogInputProvider inputProvider = new ModdedDialogInputProvider();

    public ModdedDialogFactory(ModdedPlatform platform) {
        this.platform = platform;
    }

    @Override
    public ModdedDialogBodyProvider bodyProvider() {
        return bodyProvider;
    }

    @Override
    public ModdedDialogInputProvider inputProvider() {
        return inputProvider;
    }

    @Override
    public Dialog createDialog(Component title, List<DialogBody> body, List<DialogInput> inputs, Component saveLabel, Component cancelLabel, BiConsumer<DialogResponseView, Audience> saveAction, ClickCallback.Options callbackOptions) {
        Identifier actionId = platform.registerCustomClickAction(saveAction, callbackOptions);
        return ModdedDialog.fromNative(platform, new ConfirmationDialog(
                new CommonDialogData(
                        platform.getAdventure().asNative(title),
                        Optional.empty(),
                        true,
                        true,
                        DialogAction.CLOSE,
                        body.stream().map(ModdedDialogBody::toNative).toList(),
                        inputs.stream().map(ModdedDialogInput::toNative).toList()),
                new ActionButton(
                        new CommonButtonData(
                                platform.getAdventure().asNative(saveLabel),
                                CommonButtonData.DEFAULT_WIDTH
                        ),
                        Optional.of(new CustomAll(actionId, Optional.empty()))),
                new ActionButton(
                        new CommonButtonData(
                                platform.getAdventure().asNative(cancelLabel),
                                CommonButtonData.DEFAULT_WIDTH
                        ),
                        Optional.empty())
        ));
    }
}
