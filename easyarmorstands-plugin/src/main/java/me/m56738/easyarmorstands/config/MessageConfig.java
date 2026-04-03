package me.m56738.easyarmorstands.config;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.message.MessageStyle;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;

@ConfigSerializable
public class MessageConfig {
    public Map<MessageStyle, String> format;
    public boolean serverSideTranslation;

    public static TypeToken<Map<MessageStyle, String>> formatType() {
        return new TypeToken<Map<MessageStyle, String>>() {
        };
    }
}
