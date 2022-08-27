package me.m56738.easyarmorstands.module.skeleton;

import me.m56738.easyarmorstands.module.Module;
import me.m56738.easyarmorstands.module.ModuleFactory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class SkeletonModuleFactory implements ModuleFactory {
    @Override
    public String name() {
        return "skeleton";
    }

    @Override
    public boolean supported() {
        return PotionEffectType.getByName("GLOWING") != null;
    }

    @Override
    public Module create(Plugin plugin) {
        return new SkeletonModule(plugin);
    }
}
