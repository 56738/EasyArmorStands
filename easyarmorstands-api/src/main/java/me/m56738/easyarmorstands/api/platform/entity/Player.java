package me.m56738.easyarmorstands.api.platform.entity;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;

public interface Player extends Entity, CommandSender {
    boolean isSneaking();

    boolean isFlying();

    boolean isCreativeMode();

    Location getEyeLocation();

    void giveItem(Item item);

    void openMenu(Menu menu);
}
