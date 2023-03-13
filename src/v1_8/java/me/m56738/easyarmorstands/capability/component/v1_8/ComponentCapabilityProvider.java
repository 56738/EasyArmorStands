package me.m56738.easyarmorstands.capability.component.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ComponentCapabilityProvider implements CapabilityProvider<ComponentCapability> {
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.FALLBACK;
    }

    @Override
    public ComponentCapability create(Plugin plugin) {
        return new ComponentCapabilityImpl();
    }

    private static class ComponentCapabilityImpl implements ComponentCapability {
        @Override
        public Component getCustomName(Entity entity) {
            return LegacyComponentSerializer.legacySection().deserializeOrNull(entity.getCustomName());
        }

        @Override
        public void setCustomName(Entity entity, Component name) {
            entity.setCustomName(LegacyComponentSerializer.legacySection().serializeOrNull(name));
        }

        @Override
        public void setDisplayName(ItemMeta meta, Component displayName) {
            if (displayName == Component.empty()) {
                meta.setDisplayName(ChatColor.RESET.toString());
                return;
            }
            meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(
                    displayName.applyFallbackStyle(TextDecoration.ITALIC.withState(false))));
        }

        @Override
        public void setLore(ItemMeta meta, List<Component> lore) {
            List<String> legacyLore = new ArrayList<>(lore.size());
            for (Component component : lore) {
                legacyLore.add(LegacyComponentSerializer.legacySection().serialize(
                        component.applyFallbackStyle(TextDecoration.ITALIC.withState(false))));
            }
            meta.setLore(legacyLore);
        }
    }
}
