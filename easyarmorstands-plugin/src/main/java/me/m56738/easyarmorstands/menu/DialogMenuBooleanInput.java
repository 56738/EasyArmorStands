package me.m56738.easyarmorstands.menu;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import me.m56738.easyarmorstands.api.menu.MenuBooleanInput;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Locale;

public class DialogMenuBooleanInput extends DialogMenuInput implements MenuBooleanInput {
    private boolean value;

    public DialogMenuBooleanInput(Component label, Locale locale, boolean value) {
        super(label, locale);
        this.value = value;
    }

    @Override
    public boolean getValue() {
        return value;
    }

    @Override
    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public DialogInput build() {
        return DialogInput.bool(key(), GlobalTranslator.render(label(), locale()))
                .initial(value)
                .build();
    }

    @Override
    public void processResponse(DialogResponseView response) {
        Boolean value = response.getBoolean(key());
        if (value != null) {
            setValue(value);
        }
    }
}
