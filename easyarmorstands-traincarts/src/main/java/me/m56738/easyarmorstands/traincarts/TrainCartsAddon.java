package me.m56738.easyarmorstands.traincarts;

import static me.m56738.easyarmorstands.api.menu.MenuSlotTypeRegistry.menuSlotTypeRegistry;

public class TrainCartsAddon {
    public TrainCartsAddon() {
        menuSlotTypeRegistry().register(new TrainCartsModelListingSlotType());
    }
}
