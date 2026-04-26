package me.m56738.easyarmorstands.api.editor.layer;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandlerContext;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.LateUpdateContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeHideContext;
import me.m56738.easyarmorstands.api.editor.node.NodeShowContext;
import me.m56738.easyarmorstands.api.editor.node.NodeUpdateContext;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public abstract class AbstractLayer implements NodeLayer {
    private final Session session;
    private final Map<Node, NodeState> nodes = new HashMap<>();
    private boolean visible;
    private @Nullable FocusedButton focusedButton;

    public AbstractLayer(Session session) {
        this.session = session;
    }

    @Override
    public void addNode(Node node) {
        NodeState state = new NodeState(node);
        NodeState old = nodes.put(node, state);
        if (visible) {
            if (old != null) {
                old.hide();
            }
            state.show();
        }
    }

    @Override
    public void removeNode(Node node) {
        NodeState old = nodes.remove(node);
        if (old != null) {
            if (visible) {
                old.hide();
            }
        }
    }

    @Override
    public void onEnter(EnterContext context) {
        visible = true;
        for (NodeState node : nodes.values()) {
            node.show();
        }
    }

    @Override
    public void onExit(ExitContext context) {
        visible = false;
        for (NodeState node : nodes.values()) {
            node.hide();
        }
    }

    @Override
    public void onUpdate(UpdateContext context) {
        EyeRay ray = context.eyeRay();
        focusedButton = null;
        int bestPriority = Integer.MIN_VALUE;
        double bestDistance = Double.POSITIVE_INFINITY;
        List<ButtonResult> results = new ArrayList<>();
        for (NodeState node : nodes.values()) {
            for (Map.Entry<Button, ButtonState> entry : node.buttons.entrySet()) {
                Button button = entry.getKey();
                ButtonState state = entry.getValue();
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
                        focusedButton = new FocusedButton(state, position);
                        bestPriority = priority;
                        bestDistance = distance;
                    }
                }
                results.clear();
            }
        }
        if (focusedButton != null) {
            focusedButton.state.handler.onUpdate(new ButtonHandlerContext() {
                @Override
                public void addInput(Input input) {
                    focusedButton.inputs.add(input);
                }
            });
            for (Input input : focusedButton.inputs) {
                context.addInput(input);
            }
        }
        for (NodeState node : nodes.values()) {
            node.update(context);
        }
    }

    @Override
    public void onLateUpdate(LateUpdateContext context) {
        if (focusedButton != null) {
            boolean anyAvailable = false;
            for (Input input : focusedButton.inputs) {
                if (context.isInputAvailable(input)) {
                    anyAvailable = true;
                    break;
                }
            }
            if (!anyAvailable) {
                focusedButton = null;
            }
        }

        Button bestButton = focusedButton != null ? focusedButton.state.button : null;
        for (NodeState node : nodes.values()) {
            for (ButtonState state : node.buttons.values()) {
                Button button = state.button;
                ButtonHandler handler = state.handler;
                button.updatePreview(button == bestButton, handler.isSelected());
            }
        }
        Component targetName;
        if (focusedButton != null) {
            targetName = focusedButton.state.handler.getName();
        } else {
            targetName = Component.empty();
        }
        context.setSubtitle(targetName);
    }

    private static class FocusedButton {
        private final ButtonState state;
        private final Vector3dc cursor;
        private final List<Input> inputs = new ArrayList<>();

        private FocusedButton(ButtonState state, Vector3dc cursor) {
            this.state = state;
            this.cursor = cursor;
        }
    }

    private static class NodeState {
        private final Node node;
        private final Map<Button, ButtonState> buttons = new HashMap<>();

        private NodeState(Node node) {
            this.node = node;
        }

        public void show() {
            ShowContextImpl context = new ShowContextImpl();
            node.onShow(context);
            for (Map.Entry<Button, ButtonHandler> entry : context.buttons.entrySet()) {
                Button button = entry.getKey();
                ButtonHandler handler = entry.getValue();
                ButtonState state = new ButtonState(button, handler);
                button.update();
                button.updatePreview(false, false);
                button.showPreview();
                buttons.put(button, state);
            }
        }

        public void hide() {
            HideContextImpl context = new HideContextImpl();
            node.onHide(context);
            for (Button button : buttons.keySet()) {
                button.hidePreview();
            }
            buttons.clear();
        }

        public void update(UpdateContext updateContext) {
            UpdateContextImpl context = new UpdateContextImpl();
            node.onUpdate(context);
            context.inputs.forEach(updateContext::addInput);
            for (Map.Entry<Button, ButtonHandler> entry : context.addedButtons.entrySet()) {
                ButtonState state = new ButtonState(entry.getKey(), entry.getValue());
                ButtonState old = buttons.put(state.button, state);
                if (old != null) {
                    old.button.hidePreview();
                }
                state.button.update();
                state.button.updatePreview(false, state.handler.isSelected());
                state.button.showPreview();
            }
            for (Button button : context.removedButtons) {
                ButtonState state = buttons.remove(button);
                if (state != null) {
                    button.hidePreview();
                }
            }
        }
    }

    private static class ButtonState {
        private final Button button;
        private final ButtonHandler handler;

        private ButtonState(Button button, ButtonHandler handler) {
            this.button = button;
            this.handler = handler;
        }
    }

    private static class ShowContextImpl implements NodeShowContext {
        private final Map<Button, ButtonHandler> buttons = new HashMap<>();

        @Override
        public void addButton(Button button, ButtonHandler handler) {
            buttons.put(button, handler);
        }
    }

    private static class HideContextImpl implements NodeHideContext {
    }

    private static class UpdateContextImpl implements NodeUpdateContext {
        private final Map<Button, ButtonHandler> addedButtons = new HashMap<>();
        private final List<Button> removedButtons = new ArrayList<>();
        private final List<Input> inputs = new ArrayList<>();

        @Override
        public void addInput(Input input) {
            inputs.add(input);
        }

        @Override
        public void addButton(Button button, ButtonHandler handler) {
            addedButtons.put(button, handler);
        }

        @Override
        public void removeButton(Button button) {
            removedButtons.add(button);
        }
    }
}
