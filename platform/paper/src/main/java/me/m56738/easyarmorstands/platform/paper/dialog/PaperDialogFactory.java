package me.m56738.easyarmorstands.platform.paper.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import me.m56738.easyarmorstands.platform.dialog.DialogBody;
import me.m56738.easyarmorstands.platform.dialog.DialogBodyProvider;
import me.m56738.easyarmorstands.platform.dialog.DialogFactory;
import me.m56738.easyarmorstands.platform.dialog.DialogInput;
import me.m56738.easyarmorstands.platform.dialog.DialogInputProvider;
import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;

import java.util.List;
import java.util.function.BiConsumer;

public class PaperDialogFactory implements DialogFactory {
    private final PaperDialogBodyProvider bodyProvider = new PaperDialogBodyProvider();
    private final PaperDialogInputProvider inputProvider = new PaperDialogInputProvider();

    @Override
    public DialogBodyProvider bodyProvider() {
        return bodyProvider;
    }

    @Override
    public DialogInputProvider inputProvider() {
        return inputProvider;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public PaperDialog createDialog(Component title, List<DialogBody> body, List<DialogInput> inputs, Component saveLabel, Component cancelLabel, BiConsumer<DialogResponseView, Audience> saveAction, ClickCallback.Options callbackOptions) {
        return PaperDialog.fromNative(Dialog.create(b -> b.empty()
                .base(DialogBase.builder(title)
                        .body(body.stream().map(PaperDialogBody::toNative).toList())
                        .inputs(inputs.stream().map(PaperDialogInput::toNative).toList())
                        .build())
                .type(DialogType.confirmation(
                        ActionButton.builder(saveLabel)
                                .action(DialogAction.customClick((response, audience) -> saveAction.accept(PaperDialogResponseView.fromNative(response), audience), callbackOptions))
                                .build(),
                        ActionButton.builder(cancelLabel)
                                .build()))));
    }
}
