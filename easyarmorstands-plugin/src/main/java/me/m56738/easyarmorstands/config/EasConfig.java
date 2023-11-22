package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.message.MessageStyle;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;

@ConfigSerializable
public class EasConfig {
    public boolean updateCheck = true;
    public boolean serverSideTranslation = true;
    public ItemTemplate tool;
    public Map<MessageStyle, String> messageFormats;
    public MenuSlotFactory menuBackground;
    public double editorScaleMinDistance = 5;
    public double editorScaleMaxDistance = 64;
    public double editorRange = 64;
    public double editorSelectionRange = 10;
    public double editorLookThreshold = 0.1;
    public int interpolationTicks = 1;
}
