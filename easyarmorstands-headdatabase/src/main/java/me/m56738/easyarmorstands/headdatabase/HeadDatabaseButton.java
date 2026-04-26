package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class HeadDatabaseButton implements MenuButton {
    private static final Key KEY = EasyArmorStands.key("headdatabase");

    @Override
    public Key key() {
        return KEY;
    }

    @Override
    public MenuIcon icon() {
        return MenuIcon.of(Material.PLAYER_HEAD);
    }

    @Override
    public MenuButtonCategory category() {
        return MenuButtonCategory.HEADER;
    }

    @Override
    public Component name() {
        return Component.translatable("easyarmorstands.menu.headdb.open");
    }

    @Override
    public List<Component> description() {
        return List.of(Component.translatable("easyarmorstands.menu.headdb.description"));
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (!context.isLeftClick()) {
            return;
        }
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        Player player = context.player();
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            player.setItemOnCursor(null);
            player.performCommand("headdb");
        });
    }
}
