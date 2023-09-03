package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.EasyArmorStands;

public class HeadDatabaseAddon {
    public HeadDatabaseAddon() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        EasyArmorStands.get().menuSlotTypeRegistry().register(new HeadDatabaseSlotType());
        plugin.getServer().getPluginManager().registerEvents(new HeadDatabaseListener(plugin), plugin);
    }
}
