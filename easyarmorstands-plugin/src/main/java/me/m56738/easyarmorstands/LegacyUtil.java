package me.m56738.easyarmorstands;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@Deprecated
@NullMarked
public final class LegacyUtil {
    private LegacyUtil() {
    }

    public static ItemStack wrapItem(@Nullable ItemStack item) {
        return Objects.requireNonNullElse(item, ItemStack.empty());
    }
}
