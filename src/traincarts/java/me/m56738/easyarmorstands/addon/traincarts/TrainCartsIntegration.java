package me.m56738.easyarmorstands.addon.traincarts;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.EasPlayer;
import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;

public class TrainCartsIntegration {
    private final EasyArmorStands plugin;

    public TrainCartsIntegration(EasyArmorStands plugin) {
        this.plugin = plugin;
    }
}
