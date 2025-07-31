package me.m56738.easyarmorstands.fabric.element;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.fabric.api.EasyArmorStandsEvents;
import me.m56738.easyarmorstands.modded.element.ModdedEntityElementInitializer;

public class FabricEntityElementListener {
    private final ModdedEntityElementInitializer initializer;

    public FabricEntityElementListener(EasyArmorStandsCommonProvider easProvider) {
        this.initializer = new ModdedEntityElementInitializer(easProvider);
    }

    public void register() {
        EasyArmorStandsEvents.INITIALIZE_DEFAULT_ELEMENT.register(initializer::registerProperties);
    }
}
