package me.m56738.easyarmorstands.config.integration;

import me.m56738.easyarmorstands.config.integration.lands.LandsConfig;
import me.m56738.easyarmorstands.config.integration.towny.TownyConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class IntegrationConfig {
    @Setting("BentoBox")
    public IntegrationEntryConfig bentoBox;
    @Setting("WorldGuard")
    public IntegrationEntryConfig worldGuard;
    @Setting("PlotSquared")
    public IntegrationEntryConfig plotSquared;
    @Setting("FancyHolograms")
    public IntegrationEntryConfig fancyHolograms;
    @Setting("GriefDefender")
    public IntegrationEntryConfig griefDefender;
    @Setting("GriefPrevention")
    public IntegrationEntryConfig griefPrevention;
    @Setting("HuskClaims")
    public IntegrationEntryConfig huskClaims;
    @Setting("Lands")
    public LandsConfig lands;
    @Setting("Residence")
    public IntegrationEntryConfig residence;
    @Setting("Towny")
    public TownyConfig towny;
    @Setting("HeadDatabase")
    public IntegrationEntryConfig headDatabase;
    @Setting("TrainCarts")
    public IntegrationEntryConfig trainCarts;
}
