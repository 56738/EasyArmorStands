package me.m56738.easyarmorstands.capability.textdisplay.v1_19_4;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.Plugin;

public class TextDisplayCapabilityProvider implements CapabilityProvider<TextDisplayCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("org.bukkit.entity.TextDisplay");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.FALLBACK;
    }

    @Override
    public TextDisplayCapability create(Plugin plugin) {
        return new TextDisplayCapabilityImpl();
    }

    private static class TextDisplayCapabilityImpl implements TextDisplayCapability {
        private final LegacyComponentSerializer serializer = BukkitComponentSerializer.legacy();

        @Override
        public Component getText(TextDisplay entity) {
            return serializer.deserializeOrNull(entity.getText());
        }

        @Override
        public void setText(TextDisplay entity, Component text) {
            entity.setText(serializer.serializeOrNull(text));
        }
    }
}
