package me.m56738.easyarmorstands.lands;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.enums.FlagTarget;
import me.angeschossen.lands.api.flags.enums.RoleFlagCategory;
import me.angeschossen.lands.api.flags.type.Flags;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import me.angeschossen.lands.api.role.Role;
import me.m56738.easyarmorstands.config.integration.lands.LandsFlagConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Locale;

public class LandsAddon implements Addon {
    private static final String FLAG_NAME = "easyarmorstands_edit";

    private final Plugin plugin;
    private final EasyArmorStandsPaperImpl eas;
    private final LandsIntegration integration;

    private RegionPrivilegeChecker privilegeChecker;
    private RoleFlag flag;

    public LandsAddon(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        this.plugin = plugin;
        this.integration = LandsIntegration.of(plugin);
        this.eas = eas;
        integration.onLoad(this::onLoad);
    }

    private static boolean hasSimilarFlag(Role role) {
        return role != null && role.hasFlag(Flags.BLOCK_PLACE);
    }

    private void onLoad() {
        flag = RoleFlag.of(integration, FlagTarget.PLAYER, RoleFlagCategory.ACTION, FLAG_NAME);
        configureFlag();
    }

    private void configureFlag() {
        LandsFlagConfig flagConfig = eas.getConfiguration().integration.lands.flag;
        flag.setIcon(PaperItemStack.toNative(flagConfig.icon.render(Locale.US)));
        flag.setDisplayName(flagConfig.displayName);
        flag.setDescription(flagConfig.description);
        flag.setDisplay(flagConfig.display);
        flag.setUpdatePredicate(LandsAddon::hasSimilarFlag);
    }

    @Override
    public String name() {
        return "Lands";
    }

    @Override
    public void enable() {
        privilegeChecker = new LandsPrivilegeChecker(integration, flag);
        eas.regionPrivilegeManager().registerPrivilegeChecker(plugin, privilegeChecker);
    }

    @Override
    public void disable() {
        eas.regionPrivilegeManager().unregisterPrivilegeChecker(privilegeChecker);
    }

    @Override
    public void reload() {
        if (flag != null) {
            configureFlag();
        }
    }
}
