package me.m56738.easyarmorstands.addon.display;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;

public class DisplayAddon implements Addon {
    private JOMLMapper mapper;

    public DisplayAddon() {
        try {
            mapper = new JOMLMapper();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public boolean isSupported() {
        return mapper != null;
    }

    @Override
    public String getName() {
        return "display entity";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        DisplaySessionListener listener = new DisplaySessionListener(mapper);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
