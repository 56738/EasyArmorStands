package me.m56738.easyarmorstands.editor.input.selection;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class SelectGroupInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.select.group");
    private static final Style STYLE = Style.style(NamedTextColor.GREEN);
    private final Session session;
    private final Collection<SelectableElement> elements;

    public SelectGroupInput(Session session, Collection<SelectableElement> elements) {
        this.session = session;
        this.elements = elements;
    }

    @Override
    public @NotNull Component name() {
        return NAME;
    }

    @Override
    public @NotNull Style style() {
        return STYLE;
    }

    @Override
    public ClickContext.@NotNull Type clickType() {
        return ClickContext.Type.RIGHT_CLICK;
    }

    @Override
    public boolean allowSneak() {
        return false;
    }

    @Override
    public void execute(@NotNull ClickContext context) {
        int size = elements.size();
        if (size > 1) {
            Group group = new Group(session);
            for (SelectableElement element : elements) {
                group.addMember(element);
            }
            session.pushNode(new GroupRootNode(group));
        } else {
            SelectableElement element = elements.iterator().next();
            session.pushNode(element.createNode(session));
        }
    }
}
