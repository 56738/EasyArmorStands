package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.property.button.PropertyButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class BlockDisplaySlot extends PropertyButton<BlockData> {
    public BlockDisplaySlot(Property<BlockData> property, MenuIcon icon, List<Component> description) {
        super(property, icon, description);
    }

    @Override
    public MenuIcon icon() {
        BlockData blockData = property.getValue();
        Material material = blockData.getMaterial();
        if (!material.isItem()) {
            return super.icon();
        }
        return MenuIcon.of(material);
    }

    @Override
    protected void populateDescription(List<Component> description) {
        super.populateDescription(description);
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (context.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(context.player())
                    .handlePropertyShiftClick(property);
        }
    }
}
