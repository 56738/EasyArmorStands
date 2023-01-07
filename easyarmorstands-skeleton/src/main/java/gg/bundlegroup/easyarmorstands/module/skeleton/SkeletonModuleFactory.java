package gg.bundlegroup.easyarmorstands.module.skeleton;

import gg.bundlegroup.easyarmorstands.module.ModuleFactory;
import gg.bundlegroup.easyarmorstands.module.Module;
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
