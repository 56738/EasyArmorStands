package me.m56738.easyarmorstands.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.translation.GlobalTranslator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class DialogBuilder {
    private final Locale locale;
    private final List<DialogEntry> entries = new ArrayList<>();
    private Component title = Component.empty();

    public DialogBuilder(Locale locale) {
        this.locale = locale;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    public void addEntry(DialogEntry entry) {
        entries.add(entry);
    }

    public Dialog build() {
        ClickCallback.Options callbackOptions = ClickCallback.Options.builder()
                .lifetime(Duration.ofHours(1))
                .uses(1)
                .build();

        Set<DialogBodyEntry> bodyEntries = new LinkedHashSet<>();
        List<DialogInput> inputs = new ArrayList<>();
        for (DialogEntry entry : entries) {
            entry.populateBody(bodyEntries);
            entry.populateInputs(inputs, locale);
        }

        List<DialogBody> body = new ArrayList<>();
        for (DialogBodyEntry entry : bodyEntries) {
            entry.populateBody(body, locale);
        }

        return Dialog.create(b -> b.empty()
                .base(DialogBase.builder(GlobalTranslator.render(title, locale))
                        .body(body)
                        .inputs(inputs)
                        .build())
                .type(DialogType.confirmation(
                        ActionButton.builder(GlobalTranslator.render(Component.translatable("easyarmorstands.button.save"), locale))
                                .action(DialogAction.customClick(this::save, callbackOptions))
                                .build(),
                        ActionButton.builder(GlobalTranslator.render(Component.translatable("easyarmorstands.button.cancel"), locale))
                                .build())));
    }

    private void save(DialogResponseView response, Audience audience) {
        for (DialogEntry entry : entries) {
            entry.save(response);
        }
        for (DialogEntry entry : entries) {
            entry.commit();
        }
    }
}
