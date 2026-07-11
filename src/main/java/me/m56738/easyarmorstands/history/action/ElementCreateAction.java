package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ElementCreateAction extends ElementPresenceAction {
    public ElementCreateAction(@NotNull Element element) {
        super(element);
    }

    @Override
    public boolean execute(EasPlayer player) {
        return create(player);
    }

    @Override
    public boolean undo(EasPlayer player) {
        return destroy(player);
    }

    @Override
    public Component describe() {
        return Message.component("easyarmorstands.history.create-element", getType().getDisplayName());
    }
}
