package me.m56738.easyarmorstands.config.override;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class VersionOverride {
    public String name;
    public VersionOverrideCondition condition;
    public List<String> path;
    public ConfigurationNode value;
}
