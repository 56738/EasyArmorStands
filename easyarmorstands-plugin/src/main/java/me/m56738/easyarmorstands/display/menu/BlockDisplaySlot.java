package me.m56738.easyarmorstands.display.menu;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.property.button.PropertyButton;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlockDisplaySlot extends PropertyButton<BlockData> {
    public BlockDisplaySlot(Property<BlockData> property, PropertyContainer container, SimpleItemTemplate item) {
        super(property, container, item);
    }

    private ItemStack getItem(Material material) {
        if (!material.isItem()) {
            return null;
        }
        ItemStack item = new ItemStack(material);
        if (item.getItemMeta() == null) {
            return null;
        }
        return item;
    }

    @Override
    protected SimpleItemTemplate prepareTemplate(SimpleItemTemplate template) {
        BlockData blockData = property.getValue();
        Material material = blockData.getMaterial();
        ItemStack item = getItem(material);
        if (item != null) {
            return super.prepareTemplate(template).withTemplate(item);
        }
        return super.prepareTemplate(template);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(property, click);
        }
    }
}
