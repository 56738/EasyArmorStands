package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.message.MessageStyle;
import net.kyori.adventure.text.Component;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;

@ConfigSerializable
public class EasConfig {
    public boolean updateCheck = true;
    public boolean serverSideTranslation = true;
    public SimpleItemTemplate tool;
    public Map<MessageStyle, String> messageFormats;
    public MenuSlotFactory menuBackground;
    public double editorScaleMinDistance = 5;
    public double editorScaleMaxDistance = 64;
    public double editorRange = 64;
    public double editorSelectionRange = 10;
    public double editorLookThreshold = 0.1;
    public double editorSelectionDistance = 128.0;
    public int editorSelectionLimit = 64;
    public int editorButtonLimit = 128;
    public int interpolationTicks = 1;
    public Component swapHandsButton;
}
