package me.m56738.easyarmorstands.menu;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import me.m56738.easyarmorstands.api.menu.MenuTextInput;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Locale;

public class DialogMenuTextInput extends DialogMenuInput implements MenuTextInput {
    private String value;

    public DialogMenuTextInput(Component label, Locale locale, String value) {
        super(label, locale);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public DialogInput build() {
        return DialogInput.text(key(), GlobalTranslator.render(label(), locale()))
                .initial(value)
                .maxLength(1024 * 1024 * 4)
                .build();
    }

    @Override
    public void processResponse(DialogResponseView response) {
        String value = response.getText(key());
        if (value != null) {
            setValue(value);
        }
    }
}
