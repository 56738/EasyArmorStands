package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.profile.Profile;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ModdedItemStack extends ItemStack, ModdedPlatformHolder {
    net.minecraft.world.item.ItemStack getNative();

    static ModdedItemStack empty(ModdedPlatform platform) {
        return ModdedItemStack.fromNative(platform, net.minecraft.world.item.ItemStack.EMPTY);
    }

    static ModdedItemStack fromNative(ModdedPlatform platform, net.minecraft.world.item.@Nullable ItemStack stack) {
        if (stack == null) {
            return empty(platform);
        }
        return new ModdedItemStackImpl(platform, stack);
    }

    @Override
    default Component displayName() {
        return getPlatform().getAdventure().asAdventure(getNative().getDisplayName());
    }

    @Override
    default Component effectiveName() {
        return getPlatform().getAdventure().asAdventure(getNative().getStyledHoverName());
    }

    @Override
    default boolean isEmpty() {
        return getNative().isEmpty();
    }

    @Override
    default @Nullable Profile getProfile() {
        return null;
    }

    @Override
    default ItemStack withType(ItemType type) {
        return null;
    }

    @Override
    default ItemStack withCustomName(Component customName) {
        return null;
    }

    @Override
    default ItemStack withItemName(Component itemName) {
        return null;
    }

    @Override
    default ItemStack withHideTooltip(boolean hideTooltip) {
        return null;
    }

    @Override
    default ItemStack withCustomModelData(@Nullable Integer data) {
        return null;
    }

    @Override
    default ItemStack withEnchantmentGlintOverride(boolean value) {
        return null;
    }

    @Override
    default ItemStack withLore(List<Component> lore) {
        return null;
    }

    @Override
    default ItemStack withProfile(Profile profile) {
        return null;
    }

    @Override
    default ItemStack withHiddenComponents() {
        return null;
    }
}
