package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class EditorConfig {
    public EditorDiscoveryConfig discovery;
    public EditorButtonConfig button;
    public InputHintsConfig inputHints;
    public EditorScaleConfig scale;
    public EditorSelectionConfig selection;
    public SimpleItemTemplate tool;
    public boolean allowEntities;
    public boolean allowPlayers;
    public boolean flattenArmorStands;
}
