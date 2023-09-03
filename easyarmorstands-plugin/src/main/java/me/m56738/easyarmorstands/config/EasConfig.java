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
}
