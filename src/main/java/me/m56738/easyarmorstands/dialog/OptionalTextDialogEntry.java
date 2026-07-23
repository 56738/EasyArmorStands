package me.m56738.easyarmorstands.dialog;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.platform.dialog.DialogInput;
import me.m56738.easyarmorstands.platform.dialog.DialogInputProvider;
import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
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
    private final MiniMessage serializer = EasyArmorStandsCommon.miniMessage();

    public OptionalTextDialogEntry(String key, Property<Optional<Component>> property) {
        this.key = key;
        this.property = property;
    }

    @Override
    public void populateBody(Collection<DialogBodyEntry> body) {
        body.add(new TextDialogBodyEntry());
    }

    @Override
    public void populateInputs(Collection<DialogInput> inputs, Locale locale, DialogInputProvider provider) {
        String text = serializer.serialize(property.getValue().orElse(Component.empty()));
        if (property.getValue().equals(Optional.of(Component.empty()))) {
            text = "<empty>";
        }
        Component label = GlobalTranslator.render(property.getType().getName(), locale);
        inputs.add(provider.createText(key, label, text));
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
