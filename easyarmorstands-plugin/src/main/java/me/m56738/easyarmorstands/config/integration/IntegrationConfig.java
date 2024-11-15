package me.m56738.easyarmorstands.config.integration;

import me.m56738.easyarmorstands.config.integration.lands.LandsConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class IntegrationConfig {
    @Setting("WorldGuard")
    public IntegrationEntryConfig worldGuard;
    @Setting("PlotSquared")
    public IntegrationEntryConfig plotSquared;
    @Setting("GriefDefender")
    public IntegrationEntryConfig griefDefender;
    @Setting("Lands")
    public LandsConfig lands;
    @Setting("Residence")
    public IntegrationEntryConfig residence;
    @Setting("HeadDatabase")
    public IntegrationEntryConfig headDatabase;
    @Setting("TrainCarts")
    public IntegrationEntryConfig trainCarts;
}
