package me.m56738.easyarmorstands.platform.paper.inventory;

import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.platform.paper.profile.PaperProfile;
import me.m56738.easyarmorstands.platform.profile.Profile;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface PaperItemStack extends ItemStack {
    static PaperItemStack fromNative(org.bukkit.inventory.@Nullable ItemStack item) {
        if (item == null) {
            return empty();
        }
        return new PaperItemStackImpl(item.clone());
    }

    static PaperItemStack empty() {
        return new PaperItemStackImpl(org.bukkit.inventory.ItemStack.empty());
    }

    org.bukkit.inventory.ItemStack getNative();

    static org.bukkit.inventory.ItemStack toNative(ItemStack item) {
        return ((PaperItemStack) item).getNative();
    }

    @Override
    default Component displayName() {
        return getNative().displayName();
    }

    @Override
    default Component effectiveName() {
        return getNative().effectiveName();
    }

    @Override
    default boolean isEmpty() {
        return getNative().isEmpty();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default @Nullable PaperProfile getProfile() {
        ResolvableProfile profile = getNative().getData(DataComponentTypes.PROFILE);
        if (profile == null) {
            return null;
        }
        return PaperProfile.fromNative(profile);
    }

    @Override
    default PaperItemStack withType(ItemType type) {
        @SuppressWarnings("deprecation")
        Material material = PaperItemType.toNative(type).asMaterial();
        if (material == null) {
            material = Material.STONE;
        }
        return PaperItemStack.fromNative(getNative().withType(material));
    }

    @SuppressWarnings("UnstableApiUsage")
    default <T> PaperItemStack withData(DataComponentType.Valued<T> type, T value) {
        org.bukkit.inventory.ItemStack item = getNative();
        item.setData(type, value);
        return PaperItemStack.fromNative(item);
    }

    @SuppressWarnings("UnstableApiUsage")
    default PaperItemStack withResetData(DataComponentType type) {
        org.bukkit.inventory.ItemStack item = getNative();
        item.resetData(type);
        return PaperItemStack.fromNative(item);
    }

    @SuppressWarnings("UnstableApiUsage")
    default <T> PaperItemStack withDataOrReset(DataComponentType.Valued<T> type, @Nullable T value) {
        if (value != null) {
            return withData(type, value);
        } else {
            return withResetData(type);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default PaperItemStack withCustomName(Component customName) {
        return withData(DataComponentTypes.CUSTOM_NAME, customName);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default PaperItemStack withItemName(Component itemName) {
        return withData(DataComponentTypes.ITEM_NAME, itemName);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default PaperItemStack withHideTooltip(boolean hideTooltip) {
        return withData(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay()
                .hideTooltip(hideTooltip)
                .build());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default PaperItemStack withCustomModelData(@Nullable Integer data) {
        return withDataOrReset(DataComponentTypes.CUSTOM_MODEL_DATA,
                data != null ? CustomModelData.customModelData()
                        .addFloat(data)
                        .build() : null);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default PaperItemStack withEnchantmentGlintOverride(boolean value) {
        return withData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, value);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default PaperItemStack withLore(List<Component> lore) {
        return withData(DataComponentTypes.LORE, ItemLore.lore(lore));
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default PaperItemStack withProfile(Profile profile) {
        return withData(DataComponentTypes.PROFILE, PaperProfile.toNative(profile));
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default PaperItemStack withHiddenComponents() {
        Set<DataComponentType> hiddenComponents = new HashSet<>(getNative().getDataTypes());
        hiddenComponents.remove(DataComponentTypes.CUSTOM_NAME);
        hiddenComponents.remove(DataComponentTypes.LORE);
        return withData(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay()
                .hiddenComponents(hiddenComponents)
                .build());
    }
}
