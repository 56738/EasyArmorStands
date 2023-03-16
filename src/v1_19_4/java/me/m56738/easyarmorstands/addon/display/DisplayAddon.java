package me.m56738.easyarmorstands.addon.display;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;

public class DisplayAddon implements Addon {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("org.bukkit.entity.Display");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "display entity";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        DisplaySessionListener listener = new DisplaySessionListener();
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
