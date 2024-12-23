package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;

public class TrainCartsAddon implements Addon {
    @Override
    public String name() {
        return "TrainCarts";
    }

    @Override
    public void enable() {
        EasyArmorStands.get().menuSlotTypeRegistry().register(new TrainCartsModelListingSlotType());
    }

    @Override
    public void disable() {
    }

    @Override
    public void reload() {
    }
}
