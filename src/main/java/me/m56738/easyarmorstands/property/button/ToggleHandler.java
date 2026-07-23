package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.message.Message;

public abstract class ToggleHandler<T> implements ButtonHandler {
    private final EasyArmorStandsCommon eas;
    protected final Property<T> property;

    public ToggleHandler(EasyArmorStandsCommon eas, Property<T> property) {
        this.eas = eas;
        this.property = property;
    }

    @Override
    public void onClick(MenuClickContext context) {
        boolean changed;
        if (context.isShiftClick()) {
            handlePropertyShiftClick(eas.getClipboard(context.player()));
            return;
        } else if (context.isLeftClick()) {
            changed = toggleNextValue();
        } else if (context.isRightClick()) {
            changed = togglePreviousValue();
        } else {
            return;
        }
        if (changed) {
            property.commit();
        } else {
            context.player().sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    protected void handlePropertyShiftClick(Clipboard clipboard) {
        clipboard.handlePropertyShiftClick(property);
    }

    protected abstract boolean toggleNextValue();

    protected boolean togglePreviousValue() {
        return toggleNextValue();
    }
}
