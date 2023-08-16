package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.Element;
import net.kyori.adventure.text.Component;

public class ElementDestroyAction extends ElementPresenceAction {
    public ElementDestroyAction(Element element) {
        super(element);
    }

    @Override
    public boolean execute(ChangeContext context) {
        return destroy(context);
    }

    @Override
    public boolean undo(ChangeContext context) {
        return create(context);
    }

    @Override
    public Component describe() {
        return Component.text("Deleted ").append(getType().getDisplayName());
    }
}
