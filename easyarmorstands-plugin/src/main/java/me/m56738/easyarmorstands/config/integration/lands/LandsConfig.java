package me.m56738.easyarmorstands.config.integration.lands;

import me.m56738.easyarmorstands.config.integration.IntegrationEntryConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class LandsConfig extends IntegrationEntryConfig {
    public LandsFlagConfig flag;
}
