package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.jetbrains.annotations.NotNull;

public abstract class ToggleButton<T> extends PropertyButton<T> {
    public ToggleButton(Element element, PropertyType<T> type, SimpleItemTemplate item) {
        super(element, type, item);
    }

    public abstract T getNextValue();

    public abstract T getPreviousValue();

    protected boolean setValue(ChangeContext context, T value) {
        return context.getProperties(getElement()).get(getType()).setValue(value);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        try (ManagedChangeContext context = EasyArmorStands.get().changeContext().create(click.player())) {
            boolean changed;
            if (click.isShiftClick()) {
                EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                        .handlePropertyShiftClick(getUntrackedProperty(), click);
                return;
            } else if (click.isLeftClick()) {
                changed = setValue(context, getNextValue());
            } else if (click.isRightClick()) {
                changed = setValue(context, getPreviousValue());
            } else {
                return;
            }
            if (changed) {
                context.commit();
                click.updateItem();
            } else {
                click.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }
}
