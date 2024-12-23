package me.m56738.easyarmorstands.display;

import me.m56738.easyarmorstands.addon.AddonFactory;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.util.ReflectionUtil;

public class DisplayAddonFactory implements AddonFactory<DisplayAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return ReflectionUtil.hasClass("org.bukkit.entity.ItemDisplay");
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGHEST;
    }

    @Override
    public DisplayAddon create() {
        return new DisplayAddon();
    }
}
