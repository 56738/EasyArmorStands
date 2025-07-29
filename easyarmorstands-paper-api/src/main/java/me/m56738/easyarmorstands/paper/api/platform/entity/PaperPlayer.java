package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperLocationAdapter;
import org.bukkit.GameMode;

public interface PaperPlayer extends PaperCommandSender, PaperEntity, Player {
    static PaperPlayer fromNative(org.bukkit.entity.Player nativePlayer) {
        return new PaperPlayerImpl(nativePlayer);
    }

    static org.bukkit.entity.Player toNative(Player player) {
        return ((PaperPlayer) player).getNative();
    }

    @Override
    org.bukkit.entity.Player getNative();

    @Override
    default boolean isSneaking() {
        return getNative().isSneaking();
    }

    @Override
    default boolean isFlying() {
        return getNative().isFlying();
    }

    @Override
    default boolean isCreativeMode() {
        return getNative().getGameMode() == GameMode.CREATIVE;
    }

    @Override
    default Location getEyeLocation() {
        return PaperLocationAdapter.fromNative(getNative().getEyeLocation());
    }

    @Override
    default void giveItem(Item item) {
        getNative().getInventory().addItem(PaperItem.toNative(item));
    }

    @Override
    default void openMenu(Menu menu) {
        // TODO
    }
}
