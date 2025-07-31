package me.m56738.easyarmorstands.common.history.action;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.history.HistoryManager;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ElementCreateAction extends ElementPresenceAction {
    public ElementCreateAction(@NotNull EasyArmorStandsCommon eas, @NotNull Element element) {
        super(eas, element);
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
        return Message.component("easyarmorstands.history.create-element", getType().getDisplayName());
    }
}
