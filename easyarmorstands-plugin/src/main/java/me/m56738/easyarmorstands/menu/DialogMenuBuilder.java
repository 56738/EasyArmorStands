package me.m56738.easyarmorstands.menu;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.DialogRegistryEntry;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuAction;
import me.m56738.easyarmorstands.api.menu.MenuBooleanInput;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuFloatInput;
import me.m56738.easyarmorstands.api.menu.MenuTextInput;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DialogMenuBuilder implements MenuBuilder {
    private final Component title;
    private final Locale locale;
    private final List<DialogMenuAction> actions = new ArrayList<>();
    private final List<DialogMenuInput> inputs = new ArrayList<>();

    public DialogMenuBuilder(Component title, Locale locale) {
        this.title = title;
        this.locale = locale;
    }

    @Override
    public MenuAction addAction(Component label, @Nullable Runnable action) {
        DialogMenuAction menuAction = new DialogMenuAction(label, locale, action);
        actions.add(menuAction);
        return menuAction;
    }

    @Override
    public MenuTextInput addTextInput(Component label, String value) {
        DialogMenuTextInput input = new DialogMenuTextInput(label, locale, value);
        inputs.add(input);
        return input;
    }

    @Override
    public MenuBooleanInput addBooleanInput(Component label, boolean value) {
        DialogMenuBooleanInput input = new DialogMenuBooleanInput(label, locale, value);
        inputs.add(input);
        return input;
    }

    @Override
    public MenuFloatInput addFloatInput(Component label, float min, float max, float value) {
        DialogMenuFloatInput input = new DialogMenuFloatInput(label, locale, min, max, value);
        inputs.add(input);
        return input;
    }

    @Override
    public Menu build() {
        return new DialogMenu(createDialog());
    }

    private Dialog createDialog() {
        return Dialog.create(builder -> configureDialog(builder.empty()));
    }

    private void configureDialog(DialogRegistryEntry.Builder builder) {
        List<DialogMenuInput> inputs = List.copyOf(this.inputs);
        builder.base(DialogBase.builder(GlobalTranslator.render(title, locale))
                .inputs(inputs.stream().map(DialogMenuInput::build).toList())
                .build());
        builder.type(DialogType.multiAction(actions.stream().map(action -> action.build(inputs)).toList())
                .build());
    }
}
