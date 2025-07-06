package me.m56738.easyarmorstands.display.menu;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.property.button.PropertyButton;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlockDisplaySlot extends PropertyButton<BlockData> {
    private final Element element;
    private final PropertyType<BlockData> type;

    public BlockDisplaySlot(Element element, PropertyType<BlockData> type, SimpleItemTemplate item) {
        super(element, type, item);
        this.element = element;
        this.type = type;
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
        BlockData blockData = element.getProperties().get(type).getValue();
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
                    .handlePropertyShiftClick(element.getProperties().get(type), click);
        }
    }
}
