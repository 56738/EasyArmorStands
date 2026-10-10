package me.m56738.easyarmorstands.paper.api;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.paper.api.region.RegionPrivilegeManager;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface EasyArmorStandsPaper extends EasyArmorStands {
    static EasyArmorStandsPaper getInstance() {
        EasyArmorStandsPaper instance = EasyArmorStandsPaperHolder.instance;
        if (instance == null) {
            throw new IllegalStateException("EasyArmorStands is not initialized");
        }
        return instance;
    }

    @Contract(pure = true)
    RegionPrivilegeManager regionPrivilegeManager();
}
