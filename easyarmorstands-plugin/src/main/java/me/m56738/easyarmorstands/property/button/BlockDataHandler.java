package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class BlockDataHandler implements ButtonHandler {
    private final Property<BlockData> property;

    public BlockDataHandler(Property<BlockData> property) {
        this.property = property;
    }

    @Override
    public void onClick(MenuClickContext context) {
    }

    @Override
    public MenuIcon modifyIcon(MenuIcon icon) {
        BlockData data = property.getValue();
        Material material = data.getPlacementMaterial();
        if (material.isAir()) {
            material = Material.GLASS_PANE;
        }
        if (material.isItem()) {
            return MenuIcon.of(icon.asItem().withType(material));
        } else {
            return icon;
        }
    }
}
