package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;

import java.util.function.Function;

public class BlockDataHandler implements ButtonHandler {
    private final EasyArmorStandsCommon eas;
    private final Property<BlockData> property;

    public BlockDataHandler(EasyArmorStandsCommon eas, Property<BlockData> property) {
        this.eas = eas;
        this.property = property;
    }

    public static Function<Property<BlockData>, BlockDataHandler> provider(EasyArmorStandsCommon eas) {
        return p -> new BlockDataHandler(eas, p);
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (context.isShiftClick()) {
            eas.getClipboard(context.player())
                    .handlePropertyShiftClick(property);
        }
    }

    @Override
    public MenuIcon modifyIcon(MenuIcon icon) {
        BlockData data = property.getValue();
        ItemType itemType = data.getPlacementItemType();
        if (itemType == null) {
            return icon;
        }
        if (itemType.key().equals(ItemTypeKeys.AIR)) {
            itemType = eas.platform().getItemType(ItemTypeKeys.GLASS_PANE);
        }
        return MenuIcon.of(icon.asItem().withType(itemType));
    }
}
