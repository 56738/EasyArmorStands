package me.m56738.easyarmorstands.neoforge.element;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.modded.element.ModdedEntityElementInitializer;
import me.m56738.easyarmorstands.neoforge.api.event.element.DefaultEntityElementInitializeEvent;
import net.neoforged.neoforge.common.NeoForge;

public class NeoForgeEntityElementListener {
    private final ModdedEntityElementInitializer initializer;

    public NeoForgeEntityElementListener(EasyArmorStandsCommonProvider easProvider) {
        this.initializer = new ModdedEntityElementInitializer(easProvider);
    }

    public void register() {
        NeoForge.EVENT_BUS.addListener(DefaultEntityElementInitializeEvent.class,
                e -> initializer.registerProperties(e.getElement()));
    }
}
