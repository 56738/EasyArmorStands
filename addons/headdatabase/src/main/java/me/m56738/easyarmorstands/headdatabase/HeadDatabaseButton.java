package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class HeadDatabaseButton implements MenuButton {
    private static final Key KEY = EasyArmorStands.key("headdatabase");

    private final EasyArmorStandsCommon eas;

    public HeadDatabaseButton(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @Override
    public Key key() {
        return KEY;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public MenuIcon icon() {
        return MenuIcon.of(PaperItemType.fromNative(ItemType.PLAYER_HEAD));
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
        Player player = context.player();
        eas.platform().getScheduler().runTask(() -> {
            player.setItemOnCursor(null);
            PaperPlayer.toNative(player).performCommand("headdb");
        });
    }
}
