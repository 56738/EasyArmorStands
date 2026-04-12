package me.m56738.easyarmorstands.property.button;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.message.Message;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("UnstableApiUsage")
public class ResolvableProfileHandler implements ButtonHandler {
    private final Property<ResolvableProfile> property;

    public ResolvableProfileHandler(Property<ResolvableProfile> property) {
        this.property = property;
    }

    @Override
    public MenuIcon modifyIcon(MenuIcon icon) {
        ItemStack item = icon.asItem().clone();
        item.setData(DataComponentTypes.PROFILE, property.getValue());
        return MenuIcon.of(item);
    }

    @Override
    public void onClick(MenuClickContext context) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        Player player = context.player();
        if (context.isShiftClick()) {
            plugin.getClipboard(player).handlePropertyShiftClick(property);
        } else if (context.isLeftClick()) {
            ItemStack item = player.getItemOnCursor();
            ResolvableProfile profile = item.getData(DataComponentTypes.PROFILE);
            if (profile != null) {
                if (property.setValue(profile)) {
                    property.commit();
                } else {
                    player.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
                }
            } else {
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    if (player.getItemOnCursor().isEmpty() && player.getGameMode() == GameMode.CREATIVE) {
                        ItemStack newItem = ItemStack.of(Material.PLAYER_HEAD);
                        newItem.setData(DataComponentTypes.PROFILE, property.getValue());
                        player.setItemOnCursor(newItem);
                    }
                });
            }
        }
    }
}
