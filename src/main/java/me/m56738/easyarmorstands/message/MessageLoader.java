package me.m56738.easyarmorstands.message;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;

public class MessageLoader {
    private static MiniMessage serializer;
    private static ConfigurationSection config;

    private MessageLoader() {
    }

    public static MiniMessage getSerializer() {
        return serializer;
    }

    public static void setSerializer(MiniMessage serializer) {
        MessageLoader.serializer = serializer;
    }

    public static ConfigurationSection getConfig() {
        return config;
    }

    public static void setConfig(ConfigurationSection config) {
        MessageLoader.config = config;
    }
}
