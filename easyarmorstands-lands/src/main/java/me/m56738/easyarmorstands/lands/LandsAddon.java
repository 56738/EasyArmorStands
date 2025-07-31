package me.m56738.easyarmorstands.lands;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.enums.FlagTarget;
import me.angeschossen.lands.api.flags.enums.RoleFlagCategory;
import me.angeschossen.lands.api.flags.type.Flags;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import me.angeschossen.lands.api.role.Role;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class LandsAddon implements Addon {
    private static final String FLAG_NAME = "easyarmorstands_edit";

    private final EasyArmorStandsPlugin plugin;
    private final LandsIntegration integration;

    private LandsPrivilegeChecker privilegeChecker;
    private RoleFlag flag;

    public LandsAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
        this.integration = LandsIntegration.of(plugin);
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
//        LandsFlagConfig flagConfig = plugin.getConfiguration().integration.lands.flag;
//        flag.setIcon(PaperItem.toNative(flagConfig.icon.render(Locale.US))); TODO
//        flag.setDisplayName(flagConfig.displayName);
//        flag.setDescription(flagConfig.description);
//        flag.setDisplay(flagConfig.display);
//        flag.setUpdatePredicate(LandsAddon::hasSimilarFlag);
    }

    @Override
    public String name() {
        return "Lands";
    }

    @Override
    public void enable() {
        privilegeChecker = new LandsPrivilegeChecker(integration, flag);
        plugin.getServer().getPluginManager().registerEvents(privilegeChecker, plugin);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(privilegeChecker);
    }

    @Override
    public void reload() {
        if (flag != null) {
            configureFlag();
        }
    }
}
