package me.m56738.easyarmorstands.menu;

import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback;
import me.m56738.easyarmorstands.api.menu.MenuAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.translation.GlobalTranslator;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class DialogMenuAction implements MenuAction {
    private final Component label;
    private final Locale locale;
    private @Nullable Runnable action;
    private boolean submit;

    public DialogMenuAction(Component label, Locale locale, @Nullable Runnable action) {
        this.label = label;
        this.locale = locale;
        this.action = action;
    }

    @Override
    public @Nullable Runnable getAction() {
        return action;
    }

    @Override
    public void setAction(@Nullable Runnable action) {
        this.action = action;
    }

    @Override
    public boolean isSubmit() {
        return submit;
    }

    @Override
    public void setSubmit(boolean submit) {
        this.submit = submit;
    }

    public ActionButton build(List<DialogMenuInput> inputs) {
        DialogAction dialogAction = null;
        if (action != null) {
            DialogActionCallback callback;
            if (submit) {
                callback = new DialogMenuSubmitCallback(action, inputs);
            } else {
                callback = new DialogMenuActionCallback(action);
            }
            dialogAction = DialogAction.customClick(callback,
                    ClickCallback.Options.builder()
                            .uses(1)
                            .lifetime(Duration.ofHours(1))
                            .build());
        }
        return ActionButton.builder(GlobalTranslator.render(label, locale))
                .action(dialogAction)
                .build();
    }
}
