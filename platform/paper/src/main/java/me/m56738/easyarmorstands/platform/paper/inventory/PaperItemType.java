package me.m56738.easyarmorstands.platform.paper.inventory;

import me.m56738.easyarmorstands.platform.inventory.ItemType;
import net.kyori.adventure.key.Key;

public interface PaperItemType extends ItemType {
    static PaperItemType fromNative(org.bukkit.inventory.ItemType type) {
        return new PaperItemTypeImpl(type);
    }

    org.bukkit.inventory.ItemType getNative();

    static org.bukkit.inventory.ItemType toNative(ItemType type) {
        return ((PaperItemType) type).getNative();
    }

    @Override
    default Key key() {
        return getNative().key();
    }

    @Override
    default PaperItemStack createItemStack(int amount) {
        return new PaperItemStackImpl(getNative().createItemStack(amount));
    }
}
