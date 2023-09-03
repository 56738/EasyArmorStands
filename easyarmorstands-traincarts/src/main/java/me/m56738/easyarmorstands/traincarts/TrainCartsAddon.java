package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.api.EasyArmorStands;

public class TrainCartsAddon {
    public TrainCartsAddon() {
        EasyArmorStands.get().menuSlotTypeRegistry().register(new TrainCartsModelListingSlotType());
    }
}
