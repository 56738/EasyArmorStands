package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public abstract class ToggleButton<T> extends PropertyButton<T> {
    public ToggleButton(Property<T> property, MenuIcon icon, List<Component> description) {
        super(property, icon, description);
    }

    public abstract T getNextValue();

    public abstract T getPreviousValue();

    protected boolean setValue(T value) {
        return property.setValue(value);
    }

    @Override
    public void onClick(MenuClickContext context) {
        boolean changed;
        if (context.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(context.player())
                    .handlePropertyShiftClick(property);
            return;
        } else if (context.isLeftClick()) {
            changed = setValue(getNextValue());
        } else if (context.isRightClick()) {
            changed = setValue(getPreviousValue());
        } else {
            return;
        }
        if (changed) {
            property.commit();
        } else {
            context.player().sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }
}
