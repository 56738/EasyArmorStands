package me.m56738.easyarmorstands.dialog;

import me.m56738.easyarmorstands.platform.dialog.Dialog;
import me.m56738.easyarmorstands.platform.dialog.DialogBody;
import me.m56738.easyarmorstands.platform.dialog.DialogFactory;
import me.m56738.easyarmorstands.platform.dialog.DialogInput;
import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
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

public class DialogBuilder {
    private final DialogFactory factory;
    private final Locale locale;
    private final List<DialogEntry> entries = new ArrayList<>();
    private Component title = Component.empty();

    public DialogBuilder(DialogFactory factory, Locale locale) {
        this.factory = factory;
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
            entry.populateInputs(inputs, locale, factory.inputProvider());
        }

        List<DialogBody> body = new ArrayList<>();
        for (DialogBodyEntry entry : bodyEntries) {
            entry.populateBody(body, locale, factory.bodyProvider());
        }

        Component saveLabel = GlobalTranslator.render(Component.translatable("easyarmorstands.button.save"), locale);
        Component cancelLabel = GlobalTranslator.render(Component.translatable("easyarmorstands.button.cancel"), locale);
        return factory.createDialog(
                GlobalTranslator.render(title, locale),
                body,
                inputs,
                saveLabel,
                cancelLabel,
                this::save,
                callbackOptions);
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
