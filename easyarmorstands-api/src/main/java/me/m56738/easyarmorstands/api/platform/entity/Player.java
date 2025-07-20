package me.m56738.easyarmorstands.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.item.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;

public interface Player extends Entity, CommandSender {
    boolean isSneaking();

    boolean isFlying();

    boolean isValid();

    Location getEyeLocation();

    void giveItem(Item item);
}
