package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.command.CommandSender;
import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.PlayerInventory;
import org.jspecify.annotations.Nullable;

import java.util.Locale;

public interface Player extends LivingEntity, CommandSender {
    boolean isOnline();

    Locale locale();

    boolean isSneaking();

    PlayerInventory getInventory();

    boolean canSee(Entity entity);

    void setItemOnCursor(@Nullable ItemStack item);

    ItemStack getItemOnCursor();

    void openInventory(Inventory inventory);

    void closeInventory();

    boolean isInventoryOpen(Inventory inventory);

    boolean isCreativeMode();

    boolean isFlying();
}
