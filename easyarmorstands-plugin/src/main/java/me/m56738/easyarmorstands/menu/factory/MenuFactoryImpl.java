package me.m56738.easyarmorstands.menu.factory;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuContext;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.menu.MenuImpl;
import net.kyori.adventure.text.minimessage.MiniMessage;

class MenuFactoryImpl implements MenuFactory {
    private final String titleTemplate;
    private final MenuSlotFactory[] slotFactories;
    private final MenuSlotFactory background;

    MenuFactoryImpl(String titleTemplate, MenuSlotFactory[] slots, MenuSlotFactory background) {
        this.titleTemplate = titleTemplate;
        this.slotFactories = slots;
        this.background = background;
    }

    @Override
    public Menu createMenu(MenuContext context) {
        MenuSlot[] slots = new MenuSlot[slotFactories.length];
        for (int i = 0; i < slots.length; i++) {
            MenuSlotFactory factory = slotFactories[i];
            if (factory != null) {
                slots[i] = factory.createSlot(context);
            }
        }
        MenuSlot backgroundSlot = getBackground().createSlot(context);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = backgroundSlot;
            }
        }
        return new MenuImpl(MiniMessage.miniMessage().deserialize(titleTemplate, context.resolver()), slots, context.locale());
    }

    private MenuSlotFactory getBackground() {
        if (background != null) {
            return background;
        } else {
            return EasyArmorStandsPlugin.getInstance().getConfiguration().menuBackground;
        }
    }
}
