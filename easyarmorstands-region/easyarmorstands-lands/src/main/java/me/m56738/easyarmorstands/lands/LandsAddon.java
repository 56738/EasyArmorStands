package me.m56738.easyarmorstands.lands;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.enums.FlagTarget;
import me.angeschossen.lands.api.flags.enums.RoleFlagCategory;
import me.angeschossen.lands.api.flags.type.Flags;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import me.angeschossen.lands.api.flags.type.parent.Flag;
import me.angeschossen.lands.api.role.Role;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.config.integration.lands.LandsFlagConfig;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.region.RegionListener;
import org.bukkit.event.HandlerList;

import java.util.Locale;

public class LandsAddon implements Addon {
    private static final String FLAG_NAME = "easyarmorstands_edit";

    private final EasyArmorStandsPlugin plugin;
    private final LandsIntegration integration;

    private RegionListener listener;
    private RoleFlag flag;

    public LandsAddon() {
        this.plugin = EasyArmorStandsPlugin.getInstance();
        this.integration = LandsIntegration.of(plugin);
        integration.onLoad(this::onLoad);
    }

    private void onLoad() {
        plugin.getLogger().info("Registering Lands flag");
        Flag<?> existing = integration.getFlagRegistry().get(FLAG_NAME);
        plugin.getLogger().info(String.valueOf(existing));

        LandsFlagConfig flagConfig = plugin.getConfiguration().integration.lands.flag;
        flag = RoleFlag.of(integration, FlagTarget.PLAYER, RoleFlagCategory.ACTION, FLAG_NAME);
        flag.setIcon(flagConfig.icon.render(Locale.US));
        flag.setDisplayName(flagConfig.displayName);
        flag.setDescription(flagConfig.description);
        flag.setDisplay(flagConfig.display);
        flag.setUpdatePredicate(LandsAddon::hasSimilarFlag);
    }

    private static boolean hasSimilarFlag(Role role) {
        return role != null && role.hasFlag(Flags.BLOCK_PLACE);
    }

    @Override
    public String name() {
        return "Lands";
    }

    @Override
    public void enable() {
        listener = new RegionListener(
                Permissions.LANDS_BYPASS,
                new LandsPrivilegeChecker(integration, flag),
                Message.error("easyarmorstands.error.lands.deny-create"),
                Message.error("easyarmorstands.error.lands.deny-select"),
                Message.error("easyarmorstands.error.lands.deny-destroy"));
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(listener);
        listener = null;
    }

    @Override
    public void reload() {
    }
}
