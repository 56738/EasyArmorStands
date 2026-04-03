package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;

public class TrainCartsAddon implements Addon {
    @Override
    public String name() {
        return "TrainCarts";
    }

    @Override
    public void enable() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.menuSlotTypeRegistry().register(new TrainCartsModelListingSlotType());
    }

    @Override
    public void disable() {
    }

    @Override
    public void reload() {
    }
}
