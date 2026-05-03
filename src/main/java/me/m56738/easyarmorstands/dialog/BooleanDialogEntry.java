package me.m56738.easyarmorstands.dialog;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Collection;
import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public class BooleanDialogEntry implements DialogEntry {
    private final String key;
    private final Property<Boolean> property;

    public BooleanDialogEntry(String key, Property<Boolean> property) {
        this.key = key;
        this.property = property;
    }

    @Override
    public void populateInputs(Collection<DialogInput> inputs, Locale locale) {
        inputs.add(DialogInput.bool(key, GlobalTranslator.render(property.getType().getName(), locale))
                .initial(property.getValue())
                .build());
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
