package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.message.Message;

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
        return Message.component("easyarmorstands.history.destroy-element", getType().getDisplayName());
    }
}
