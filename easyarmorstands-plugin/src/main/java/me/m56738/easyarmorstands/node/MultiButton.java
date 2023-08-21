package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MultiButton implements Button {
    private final Set<Button> buttons = new HashSet<>();
    private boolean focused;
    private boolean visible;

    public void addButton(Button button) {
        if (!buttons.add(button)) {
            return;
        }
        if (visible) {
            button.update();
            button.updatePreview(focused);
            button.showPreview();
        }
    }

    public void removeButton(Button button) {
        if (!buttons.remove(button)) {
            return;
        }
        if (visible) {
            button.hidePreview();
        }
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
    }

    @Override
    public void intersect(EyeRay ray, Consumer<ButtonResult> results) {
        for (Button button : buttons) {
            button.intersect(ray, results);
        }
    }

    @Override
    public void updatePreview(boolean focused) {
        this.focused = focused;
        for (Button button : buttons) {
            button.updatePreview(focused);
        }
    }

    @Override
    public void showPreview() {
        visible = true;
        for (Button button : buttons) {
            button.showPreview();
        }
    }

    @Override
    public void hidePreview() {
        visible = false;
        for (Button button : buttons) {
            button.hidePreview();
        }
    }
}
