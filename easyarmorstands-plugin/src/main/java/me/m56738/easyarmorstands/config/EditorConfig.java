package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class EditorConfig {
    public EditorDiscoveryConfig discovery;
    public EditorButtonConfig button;
    public EditorScaleConfig scale;
    public EditorSelectionConfig selection;
    public SimpleItemTemplate tool;
}
