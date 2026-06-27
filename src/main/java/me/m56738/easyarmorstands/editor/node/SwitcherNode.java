package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeHideContext;
import me.m56738.easyarmorstands.api.editor.node.NodeShowContext;
import me.m56738.easyarmorstands.api.editor.node.NodeUpdateContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;

import java.util.ArrayList;
import java.util.List;

public class SwitcherNode implements Node {
    private final List<Entry> entries;
    private final Input input = new SwitchNodeInput();
    private final List<Button> buttons = new ArrayList<>();
    private int index;
    private int lastIndex;

    public SwitcherNode(List<Entry> entries) {
        this.entries = List.copyOf(entries);
    }

    private void switchNode() {
        index++;
        if (index >= entries.size()) {
            index = 0;
        }
    }

    private Entry getEntry() {
        if (entries.isEmpty()) {
            throw new IllegalStateException("Switcher node is empty");
        }
        return entries.get(index);
    }

    @Override
    public void onShow(NodeShowContext context) {
        getEntry().node().onShow(new NodeShowContext() {
            @Override
            public void addButton(Button button, ButtonHandler handler) {
                context.addButton(button, handler);
                buttons.add(button);
            }
        });
    }

    @Override
    public void onHide(NodeHideContext context) {
        getEntry().node().onHide(context);
        buttons.clear();
    }

    @Override
    public void onUpdate(NodeUpdateContext context) {
        if (index != lastIndex) {
            entries.get(lastIndex).node().onHide(new NodeHideContext() {
            });
            for (Button button : buttons) {
                context.removeButton(button);
            }
            buttons.clear();
            entries.get(index).node().onShow(new NodeShowContext() {
                @Override
                public void addButton(Button button, ButtonHandler handler) {
                    context.addButton(button, handler);
                    buttons.add(button);
                }
            });
            lastIndex = index;
        }
        getEntry().node().onUpdate(context);
        context.addInput(input);
    }

    public record Entry(Component name, Node node) {
    }

    class SwitchNodeInput implements Input {
        private static final Style STYLE = Style.style(NamedTextColor.AQUA);

        @Override
        public Component name() {
            return getEntry().name();
        }

        @Override
        public Style style() {
            return STYLE;
        }

        @Override
        public ClickContext.Type clickType() {
            return ClickContext.Type.DROP;
        }

        @Override
        public void execute(ClickContext context) {
            switchNode();
        }
    }
}
