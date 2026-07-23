package me.m56738.easyarmorstands.paper.api;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.paper.api.region.RegionPrivilegeManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface EasyArmorStandsPaper extends EasyArmorStands {
    @Contract(pure = true)
    @NotNull RegionPrivilegeManager regionPrivilegeManager();
}
