package me.m56738.easyarmorstands.platform.dialog;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;

import java.util.List;
import java.util.function.BiConsumer;

public interface DialogFactory {
    DialogBodyProvider bodyProvider();

    DialogInputProvider inputProvider();

    Dialog createDialog(
            Component title,
            List<DialogBody> body,
            List<DialogInput> inputs,
            Component saveLabel,
            Component cancelLabel,
            BiConsumer<DialogResponseView, Audience> saveAction,
            ClickCallback.Options callbackOptions);
}
