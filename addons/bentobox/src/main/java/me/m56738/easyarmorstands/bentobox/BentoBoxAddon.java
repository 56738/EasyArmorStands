package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.Addon;
import org.bukkit.plugin.Plugin;

public class BentoBoxAddon implements Addon {
    private final Plugin plugin;
    private final EasyArmorStandsPaperImpl eas;

    private BentoBoxPrivilegeChecker privilegeChecker;

    public BentoBoxAddon(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        this.plugin = plugin;
        this.eas = eas;
    }

    @Override
    public String name() {
        return "BentoBox";
    }

    @Override
    public void enable() {
        privilegeChecker = new BentoBoxPrivilegeChecker();
        eas.regionPrivilegeManager().registerPrivilegeChecker(plugin, privilegeChecker);
    }

    @Override
    public void disable() {
        if (privilegeChecker != null) {
            eas.regionPrivilegeManager().unregisterPrivilegeChecker(privilegeChecker);
            privilegeChecker = null;
        }
    }

    @Override
    public void reload() {
    }
}
