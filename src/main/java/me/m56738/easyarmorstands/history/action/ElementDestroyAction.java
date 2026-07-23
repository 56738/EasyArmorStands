package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;

public class ElementDestroyAction extends ElementPresenceAction {
    public ElementDestroyAction(EasyArmorStandsCommon eas, Element element) {
        super(eas, element);
    }

    @Override
    public boolean execute(EasPlayer player) {
        return destroy(player);
    }

    @Override
    public boolean undo(EasPlayer player) {
        return create(player);
    }

    @Override
    public Component describe() {
        return Message.component("easyarmorstands.history.destroy-element", getType().getDisplayName());
    }
}
