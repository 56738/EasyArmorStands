package me.m56738.easyarmorstands.editor.layer;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.layer.NodeLayer;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeHideContext;
import me.m56738.easyarmorstands.api.editor.node.NodeShowContext;
import me.m56738.easyarmorstands.editor.node.MenuButtonNode;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public abstract class MenuLayer implements NodeLayer {
    private final Session session;
    private final Map<Node, NodeState> nodes = new HashMap<>();
    private final Map<MenuButton, MenuButtonNode> buttons = new HashMap<>();
    private boolean visible;

    public MenuLayer(Session session) {
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

    @Deprecated
    public final void addButton(MenuButton menuButton) {
        MenuButtonNode node = new MenuButtonNode(menuButton);
        MenuButtonNode old = buttons.put(menuButton, node);
        if (old != null) {
            removeNode(old);
        }
        addNode(node);
    }

    @Deprecated
    public final void removeButton(MenuButton menuButton) {
        MenuButtonNode node = buttons.remove(menuButton);
        if (node != null) {
            removeNode(node);
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
        Button bestButton = null;
        MenuButton bestMenuButton = null;
        Vector3dc bestCursor = null;
        int bestPriority = Integer.MIN_VALUE;
        double bestDistance = Double.POSITIVE_INFINITY;
        List<ButtonResult> results = new ArrayList<>();
        Map<MenuButton, Button> allButtons = new HashMap<>();
        for (NodeState node : nodes.values()) {
            allButtons.putAll(node.buttons);
        }
        for (Map.Entry<MenuButton, Button> entry : allButtons.entrySet()) {
            MenuButton menuButton = entry.getKey();
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
                    bestMenuButton = menuButton;
                    bestCursor = position;
                    bestPriority = priority;
                    bestDistance = distance;
                }
            }
            results.clear();
        }
        for (Map.Entry<MenuButton, Button> entry : allButtons.entrySet()) {
            MenuButton menuButton = entry.getKey();
            Button button = entry.getValue();
            button.updatePreview(button == bestButton, menuButton.isSelected());
        }
        Component targetName;
        if (bestButton != null) {
            targetName = bestMenuButton.getName();
        } else {
            targetName = Component.empty();
        }
        context.setSubtitle(targetName);
        if (bestMenuButton != null) {
            bestMenuButton.onUpdate(session, bestCursor, context);
        }
    }

    private static class NodeState {
        private final Node node;
        private final Map<MenuButton, Button> buttons = new HashMap<>();

        private NodeState(Node node) {
            this.node = node;
        }

        public void show() {
            ShowContext context = new ShowContext();
            node.onShow(context);
            for (MenuButton menuButton : context.buttons) {
                Button button = menuButton.getButton();
                button.update();
                button.updatePreview(false, menuButton.isSelected());
                button.showPreview();
                buttons.put(menuButton, button);
            }
        }

        public void hide() {
            HideContext context = new HideContext();
            node.onHide(context);
            for (Button button : buttons.values()) {
                button.hidePreview();
            }
        }
    }

    private static class ShowContext implements NodeShowContext {
        private final List<MenuButton> buttons = new ArrayList<>();

        @Override
        public void addButton(MenuButton button) {
            buttons.add(button);
        }
    }

    private static class HideContext implements NodeHideContext {
    }
}
