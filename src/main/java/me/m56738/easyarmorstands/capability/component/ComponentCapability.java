package me.m56738.easyarmorstands.capability.component;

import me.m56738.easyarmorstands.capability.Capability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Capability(name = "Chat components")
public interface ComponentCapability {
    Component getCustomName(Entity entity);

    void setCustomName(Entity entity, Component name);

    void setDisplayName(ItemMeta meta, Component displayName);

    void setLore(ItemMeta meta, List<Component> lore);
}
