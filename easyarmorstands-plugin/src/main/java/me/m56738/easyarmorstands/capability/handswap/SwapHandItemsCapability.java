package me.m56738.easyarmorstands.capability.handswap;

import me.m56738.easyarmorstands.capability.Capability;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;

@Capability(name = "Swap hand items", optional = true)
public interface SwapHandItemsCapability {
    void addListener(SwapHandItemsListener listener);

    void removeListener(SwapHandItemsListener listener);
}
