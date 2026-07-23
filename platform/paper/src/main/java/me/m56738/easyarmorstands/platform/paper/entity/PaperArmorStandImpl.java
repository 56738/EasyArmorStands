package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.ArmorStand;

record PaperArmorStandImpl(ArmorStand entity) implements PaperArmorStand {
    @Override
    public ArmorStand getNative() {
        return entity;
    }
}
