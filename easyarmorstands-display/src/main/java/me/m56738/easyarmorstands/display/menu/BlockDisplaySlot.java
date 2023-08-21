package me.m56738.easyarmorstands.display.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BlockDisplaySlot implements MenuSlot {
    private final Property<BlockData> property;

    public BlockDisplaySlot(Property<BlockData> property) {
        this.property = property;
    }

    private ItemStack getItem(BlockData blockData, Material material) {
        if (!material.isItem()) {
            return null;
        }
        ComponentCapability componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        List<Component> description = new ArrayList<>();
        boolean first = true;
        for (String part : blockData.getAsString().split("[\\[,\\]]")) {
            if (!part.isEmpty()) {
                description.add(Component.text(part, first ? NamedTextColor.AQUA : NamedTextColor.DARK_AQUA));
                first = false;
            }
        }
        description.add(Component.text("Sneak and left click a block to", NamedTextColor.GRAY));
        description.add(Component.text("place it in this block display.", NamedTextColor.GRAY));
        componentCapability.setDisplayName(meta, Component.text("Block", NamedTextColor.BLUE));
        componentCapability.setLore(meta, description);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        BlockData blockData = property.getValue();
        Material material = blockData.getMaterial();
        ItemStack item = getItem(blockData, material);
        if (item == null) {
            item = getItem(blockData, Material.OAK_STAIRS);
        }
        return item;
    }

    @Override
    public void onClick(MenuClick click) {
    }
}
