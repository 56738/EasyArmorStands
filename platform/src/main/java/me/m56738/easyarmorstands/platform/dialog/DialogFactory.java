package me.m56738.easyarmorstands.platform.dialog;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;

import java.util.List;
import java.util.function.Consumer;

public interface DialogFactory {
    DialogBodyProvider bodyProvider();

    DialogInputProvider inputProvider();

    Dialog createDialog(
            Component title,
            List<DialogBody> body,
            List<DialogInput> inputs,
            Component saveLabel,
            Component cancelLabel,
            Consumer<DialogResponseView> saveAction,
            ClickCallback.Options callbackOptions);
}
