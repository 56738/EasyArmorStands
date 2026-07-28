package me.m56738.easyarmorstands.config;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.configurate.objectmapping.ConfigSerializable;
import me.m56738.easyarmorstands.message.MessageStyle;

import java.util.Map;

@ConfigSerializable
public class MessageConfig {
    public Map<MessageStyle, String> format;

    public static TypeToken<Map<MessageStyle, String>> formatType() {
        return new TypeToken<>() {
        };
    }
}
