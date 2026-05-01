package me.m56738.easyarmorstands.dialog;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Collection;
import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public class TextDialogEntry implements DialogEntry {
    private final String key;
    private final Property<Component> property;

    public TextDialogEntry(String key, Property<Component> property) {
        this.key = key;
        this.property = property;
    }

    @Override
    public void populateBody(Collection<DialogBodyEntry> body) {
        body.add(new TextDialogBodyEntry());
    }

    @Override
    public void populateInputs(Collection<DialogInput> inputs, Locale locale) {
        inputs.add(DialogInput.text(key, GlobalTranslator.render(property.getType().getName(), locale))
                .multiline(TextDialogInput.MultilineOptions.create(null, 64))
                .initial(MiniMessage.miniMessage().serialize(property.getValue()))
                .maxLength(32768)
                .build());
    }

    @Override
    public void save(DialogResponseView response) {
        String text = response.getText(key);
        if (text != null) {
            property.setValue(MiniMessage.miniMessage().deserialize(text));
        }
    }

    @Override
    public void commit() {
        property.commit();
    }
}
