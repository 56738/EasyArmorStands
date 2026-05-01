package me.m56738.easyarmorstands.dialog;

import io.papermc.paper.registry.data.dialog.body.DialogBody;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageStyle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.List;
import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public class TextDialogBodyEntry implements DialogBodyEntry {
    @Override
    public void populateBody(List<DialogBody> body, Locale locale) {
        Component text = Component.translatable("easyarmorstands.dialog.minimessage.hint")
                .arguments(Message.format(MessageStyle.LINK, Component.translatable("easyarmorstands.dialog.minimessage"))
                        .hoverEvent(Message.hover("easyarmorstands.click-to-open-minimessage"))
                        .clickEvent(ClickEvent.openUrl("https://docs.papermc.io/adventure/minimessage/format/")));
        body.add(DialogBody.plainMessage(GlobalTranslator.render(text, locale)));
    }
}
