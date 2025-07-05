package me.m56738.easyarmorstands.menu;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import me.m56738.easyarmorstands.api.menu.MenuInput;
import net.kyori.adventure.text.Component;

import java.util.Locale;
import java.util.UUID;

public abstract class DialogMenuInput implements MenuInput {
    private final String key = UUID.randomUUID().toString().substring(0, 8);
    private final Component label;
    private final Locale locale;

    public DialogMenuInput(Component label, Locale locale) {
        this.label = label;
        this.locale = locale;
    }

    public String key() {
        return key;
    }

    public Component label() {
        return label;
    }

    public Locale locale() {
        return locale;
    }

    public abstract DialogInput build();

    public abstract void processResponse(DialogResponseView response);
}
