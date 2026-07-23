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

@SuppressWarnings("UnstableApiUsage")
public class TextDialogEntry implements DialogEntry {
    private final String key;
    private final Property<Component> property;
    private final MiniMessage serializer = EasyArmorStandsCommon.miniMessage();

    public TextDialogEntry(String key, Property<Component> property) {
        this.key = key;
        this.property = property;
    }

    @Override
    public void populateBody(Collection<DialogBodyEntry> body) {
        body.add(new TextDialogBodyEntry());
    }

    @Override
    public void populateInputs(Collection<DialogInput> inputs, Locale locale, DialogInputProvider provider) {
        Component label = GlobalTranslator.render(property.getType().getName(), locale);
        inputs.add(provider.createText(key, label, serializer.serialize(property.getValue())));
    }

    @Override
    public void save(DialogResponseView response) {
        String text = response.getText(key);
        if (text != null) {
            property.setValue(serializer.deserialize(text));
        }
    }

    @Override
    public void commit() {
        property.commit();
    }
}
