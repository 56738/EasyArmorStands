package me.m56738.easyarmorstands.platform.inventory;

import me.m56738.easyarmorstands.platform.profile.Profile;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ItemStack {
    Component displayName();

    Component effectiveName();

    boolean isEmpty();

    @Nullable Profile getProfile();

    ItemStack withType(ItemType type);

    ItemStack withCustomName(Component customName);

    ItemStack withItemName(Component itemName);

    ItemStack withHideTooltip(boolean hideTooltip);

    ItemStack withCustomModelData(@Nullable Integer data);

    ItemStack withEnchantmentGlintOverride(boolean value);

    ItemStack withLore(List<Component> lore);

    ItemStack withProfile(Profile profile);

    ItemStack withHiddenComponents();
}
