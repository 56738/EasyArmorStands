package me.m56738.easyarmorstands.dialog;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class OptionalTextDialogEntry implements DialogEntry {
    private final String key;
    private final Property<Optional<Component>> property;
    private final MiniMessage serializer = EasyArmorStandsPlugin.getInstance().getMiniMessage();

    public OptionalTextDialogEntry(String key, Property<Optional<Component>> property) {
        this.key = key;
        this.property = property;
    }

    @Override
    public void populateBody(Collection<DialogBodyEntry> body) {
        body.add(new TextDialogBodyEntry());
    }

    @Override
    public void populateInputs(Collection<DialogInput> inputs, Locale locale) {
        String text = serializer.serialize(property.getValue().orElse(Component.empty()));
        if (property.getValue().equals(Optional.of(Component.empty()))) {
            text = "<empty>";
        }
        inputs.add(DialogInput.text(key, GlobalTranslator.render(property.getType().getName(), locale))
                .multiline(TextDialogInput.MultilineOptions.create(null, 64))
                .initial(text)
                .maxLength(32768)
                .build());
    }

    @Override
    public void save(DialogResponseView response) {
        String text = response.getText(key);
        if (text != null) {
            if (text.isEmpty()) {
                property.setValue(Optional.empty());
            } else if (text.equals("<empty>")) {
                property.setValue(Optional.of(Component.empty()));
            } else {
                property.setValue(Optional.of(serializer.deserialize(text)));
            }
        }
    }

    @Override
    public void commit() {
        property.commit();
    }
}
