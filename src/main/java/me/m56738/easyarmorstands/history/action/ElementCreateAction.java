package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.Element;
import net.kyori.adventure.text.Component;

public class ElementCreateAction extends ElementPresenceAction {
    public ElementCreateAction(Element element) {
        super(element);
    }

    @Override
    public boolean execute(ChangeContext context) {
        return create(context);
    }

    @Override
    public boolean undo(ChangeContext context) {
        return destroy(context);
    }

    @Override
    public Component describe() {
        return Component.text("Spawned ").append(getType().getDisplayName());
    }
}
