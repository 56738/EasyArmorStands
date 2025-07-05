package me.m56738.easyarmorstands.menu;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import me.m56738.easyarmorstands.api.menu.MenuFloatInput;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Locale;

public class DialogMenuFloatInput extends DialogMenuInput implements MenuFloatInput {
    private final float min;
    private final float max;
    private float value;

    public DialogMenuFloatInput(Component label, Locale locale, float min, float max, float value) {
        super(label, locale);
        this.min = min;
        this.max = max;
        this.value = value;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public DialogInput build() {
        return DialogInput.numberRange(key(), GlobalTranslator.render(label(), locale()), min, max)
                .initial(value)
                .build();
    }

    @Override
    public void processResponse(DialogResponseView response) {
        Float value = response.getFloat(key());
        if (value != null) {
            setValue(value);
        }
    }
}
