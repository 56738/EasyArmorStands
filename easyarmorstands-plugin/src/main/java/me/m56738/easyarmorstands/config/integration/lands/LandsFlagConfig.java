package me.m56738.easyarmorstands.config.integration.lands;

import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class LandsFlagConfig {
    public String displayName;
    public String description;
    public boolean display;
    public SimpleItemTemplate icon;
}
