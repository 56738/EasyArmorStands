package me.m56738.easyarmorstands.platform.modded.inventory;

import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.modded.profile.ModdedProfile;
import me.m56738.easyarmorstands.platform.profile.Profile;
import net.kyori.adventure.text.Component;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.SequencedSet;

public interface ModdedItemStack extends ItemStack, ModdedPlatformHolder {
    net.minecraft.world.item.ItemStack getNative();

    static ModdedItemStack empty(ModdedPlatform platform) {
        return ModdedItemStack.fromNative(platform, net.minecraft.world.item.ItemStack.EMPTY);
    }

    static ModdedItemStack fromNative(ModdedPlatform platform, net.minecraft.world.item.@Nullable ItemStack stack) {
        if (stack == null) {
            return empty(platform);
        }
        return new ModdedItemStackImpl(platform, stack.copy());
    }

    static net.minecraft.world.item.ItemStack toNative(ItemStack item) {
        return ((ModdedItemStack) item).getNative();
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
        ResolvableProfile profile = getNative().get(DataComponents.PROFILE);
        if (profile == null) {
            return null;
        }
        return ModdedProfile.fromNative(getPlatform(), profile);
    }

    @Override
    default ItemStack withType(ItemType type) {
        if (isEmpty()) {
            return this;
        }
        net.minecraft.world.item.ItemStack oldItem = getNative();
        net.minecraft.world.item.ItemStack newItem = new net.minecraft.world.item.ItemStack(ModdedItemType.toNative(type), oldItem.getCount());
        newItem.applyComponents(oldItem.getComponents());
        return ModdedItemStack.fromNative(getPlatform(), newItem);
    }

    default <T> ItemStack withData(DataComponentType<T> type, T value) {
        net.minecraft.world.item.ItemStack item = getNative();
        item.set(type, value);
        return ModdedItemStack.fromNative(getPlatform(), item);
    }

    default ItemStack withResetData(DataComponentType<?> type) {
        net.minecraft.world.item.ItemStack item = getNative();
        item.remove(type);
        return ModdedItemStack.fromNative(getPlatform(), item);
    }

    default <T> ItemStack withDataOrReset(DataComponentType<T> type, @Nullable T value) {
        if (value != null) {
            return withData(type, value);
        } else {
            return withResetData(type);
        }
    }

    @Override
    default ItemStack withCustomName(Component customName) {
        return withData(DataComponents.CUSTOM_NAME, getPlatform().getAdventure().asNative(customName));
    }

    @Override
    default ItemStack withItemName(Component itemName) {
        return withData(DataComponents.ITEM_NAME, getPlatform().getAdventure().asNative(itemName));
    }

    @Override
    default ItemStack withHideTooltip(boolean hideTooltip) {
        return withData(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(hideTooltip, ReferenceSortedSets.emptySet()));
    }

    @Override
    default ItemStack withCustomModelData(@Nullable Integer data) {
        return withDataOrReset(DataComponents.CUSTOM_MODEL_DATA,
                data != null ? new CustomModelData(List.of((float) data), List.of(), List.of(), List.of()) : null);
    }

    @Override
    default ItemStack withEnchantmentGlintOverride(boolean value) {
        return withData(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, value);
    }

    @Override
    default ItemStack withLore(List<Component> lore) {
        return withData(DataComponents.LORE, new ItemLore(lore.stream()
                .map(getPlatform().getAdventure()::asNative)
                .toList()));
    }

    @Override
    default ItemStack withProfile(Profile profile) {
        return withData(DataComponents.PROFILE, ModdedProfile.toNative(profile));
    }

    @Override
    default ItemStack withHiddenComponents() {
        SequencedSet<DataComponentType<?>> hiddenComponents = new ReferenceLinkedOpenHashSet<>(getNative().getComponents().keySet());
        hiddenComponents.remove(DataComponents.CUSTOM_NAME);
        hiddenComponents.remove(DataComponents.LORE);
        return withData(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, hiddenComponents));
    }
}
