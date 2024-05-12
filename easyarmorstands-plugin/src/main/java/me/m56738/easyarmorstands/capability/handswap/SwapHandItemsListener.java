package me.m56738.easyarmorstands.capability.handswap;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface SwapHandItemsListener {
    boolean handleSwap(Player player);
}
