package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class EditorConfig {
    public EditorDiscoveryConfig discovery;
    public EditorMenuConfig menu;
    public EditorButtonConfig button;
    public EditorScaleConfig scale;
    public EditorSelectionConfig selection;
    public SimpleItemTemplate tool;
}
