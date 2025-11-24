package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class EditorConfig {
    public EditorDiscoveryConfig discovery;
    public EditorMenuConfig menu;
    public EditorButtonConfig button;
    public boolean inputHints;
    public EditorScaleConfig scale;
    public EditorSelectionConfig selection;
    public SimpleItemTemplate tool;
}
