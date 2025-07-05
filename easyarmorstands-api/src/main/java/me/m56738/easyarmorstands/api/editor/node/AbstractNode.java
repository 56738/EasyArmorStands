package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.button.EditorButton;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A node which can contain multiple {@link Button buttons}.
 */
public abstract class AbstractNode implements Node {
    private final Session session;
    private final Map<EditorButton, Button> buttons = new HashMap<>();
    private EditorButton targetButton;
    private Vector3dc targetCursor;
    private boolean visible;

    public AbstractNode(@NotNull Session session) {
        this.session = session;
    }

    public final void addButton(@NotNull EditorButton editorButton) {
        setButton(editorButton, editorButton.getButton());
    }

    public final void removeButton(@NotNull EditorButton editorButton) {
        setButton(Objects.requireNonNull(editorButton), null);
    }

    private void setButton(EditorButton editorButton, Button button) {
        Button oldButton;
        if (button != null) {
            oldButton = buttons.put(editorButton, button);
        } else {
            oldButton = buttons.remove(editorButton);
        }
        if (visible) {
            if (oldButton != null) {
                oldButton.hidePreview();
            }
            if (button != null) {
                button.update();
                button.updatePreview(editorButton.isAlwaysFocused());
                button.showPreview();
            }
        }
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        targetButton = null;
        visible = true;
        for (Map.Entry<EditorButton, Button> entry : buttons.entrySet()) {
            EditorButton editorButton = entry.getKey();
            Button button = entry.getValue();
            button.update();
            button.updatePreview(editorButton.isAlwaysFocused());
            button.showPreview();
        }
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        targetButton = null;
        visible = false;
        for (Button button : buttons.values()) {
            button.hidePreview();
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        EyeRay ray = context.eyeRay();
        Button bestButton = null;
        EditorButton bestMenuButton = null;
        Vector3dc bestCursor = null;
        int bestPriority = Integer.MIN_VALUE;
        double bestDistance = Double.POSITIVE_INFINITY;
        List<ButtonResult> results = new ArrayList<>();
        for (Map.Entry<EditorButton, Button> entry : buttons.entrySet()) {
            EditorButton editorButton = entry.getKey();
            Button button = entry.getValue();
            button.update();
            button.intersect(ray, results::add);
            for (ButtonResult result : results) {
                Vector3dc position = result.position();
                int priority = result.priority();
                if (priority < bestPriority) {
                    continue;
                }
                double distance = position.distanceSquared(ray.origin());
                if (priority > bestPriority || distance < bestDistance) {
                    bestButton = button;
                    bestMenuButton = editorButton;
                    bestCursor = position;
                    bestPriority = priority;
                    bestDistance = distance;
                }
            }
            results.clear();
        }
        for (Map.Entry<EditorButton, Button> entry : buttons.entrySet()) {
            EditorButton editorButton = entry.getKey();
            Button button = entry.getValue();
            button.updatePreview(button == bestButton || editorButton.isAlwaysFocused());
        }
        Component targetName;
        if (bestButton != null) {
            targetName = bestMenuButton.getName();
        } else {
            targetName = Component.empty();
        }
        context.setSubtitle(targetName);
        targetButton = bestMenuButton;
        targetCursor = bestCursor;
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            if (targetButton != null) {
                targetButton.onClick(session, targetCursor);
                return true;
            }
        }
        return false;
    }
}
