package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.modcommon.MinecraftAudiences;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public interface ModdedItemType extends ItemType, ModdedPlatformHolder {
    Item getNative();

    static ModdedItemType fromNative(ModdedPlatform platform, Item item) {
        return new ModdedItemTypeImpl(platform, item);
    }

    static Item toNative(ItemType type) {
        return ((ModdedItemType) type).getNative();
    }

    @Override
    default Key key() {
        return MinecraftAudiences.asAdventure(BuiltInRegistries.ITEM.getKey(getNative()));
    }

    @Override
    default ItemStack createItemStack(int amount) {
        return ModdedItemStack.fromNative(getPlatform(), new net.minecraft.world.item.ItemStack(getNative(), amount));
    }
}
