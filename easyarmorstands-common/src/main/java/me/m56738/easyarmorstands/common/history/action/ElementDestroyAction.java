package me.m56738.easyarmorstands.common.history.action;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.common.history.HistoryManager;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import net.kyori.adventure.text.Component;

public class ElementDestroyAction extends ElementPresenceAction {
    public ElementDestroyAction(CommonPlatform platform, HistoryManager historyManager, Element element) {
        super(platform, historyManager, element);
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
