package me.m56738.easyarmorstands.dialog;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.platform.dialog.DialogInput;
import me.m56738.easyarmorstands.platform.dialog.DialogInputProvider;
import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Collection;
import java.util.Locale;

public class BooleanDialogEntry implements DialogEntry {
    private final String key;
    private final Property<Boolean> property;

    public BooleanDialogEntry(String key, Property<Boolean> property) {
        this.key = key;
        this.property = property;
    }

    @Override
    public void populateInputs(Collection<DialogInput> inputs, Locale locale, DialogInputProvider provider) {
        Component label = GlobalTranslator.render(property.getType().getName(), locale);
        inputs.add(provider.createBoolean(key, label, property.getValue()));
    }

    @Override
    public void save(DialogResponseView response) {
        Boolean value = response.getBoolean(key);
        if (value != null) {
            property.setValue(value);
        }
    }

    @Override
    public void commit() {
        property.commit();
    }
}
