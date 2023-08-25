package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.EasyArmorStands;

import static me.m56738.easyarmorstands.api.menu.MenuSlotTypeRegistry.menuSlotTypeRegistry;

public class HeadDatabaseAddon {
    public HeadDatabaseAddon() {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        menuSlotTypeRegistry().register(new HeadDatabaseSlotType());
        plugin.getServer().getPluginManager().registerEvents(new HeadDatabaseListener(plugin), plugin);
    }
}
