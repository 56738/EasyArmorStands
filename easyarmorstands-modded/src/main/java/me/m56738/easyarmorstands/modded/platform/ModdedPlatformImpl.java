package me.m56738.easyarmorstands.modded.platform;

import me.m56738.easyarmorstands.api.platform.item.Item;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.item.ModdedItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

public class ModdedPlatformImpl implements ModdedPlatform {
    private final String modVersion;

    public ModdedPlatformImpl(String modVersion) {
        this.modVersion = modVersion;
    }

    @Override
    public String getEasyArmorStandsVersion() {
        return modVersion;
    }

    @Override
    public Item createTool() {
        ItemStack item = new ItemStack(Items.BLAZE_ROD);
        CompoundTag tag = new CompoundTag();
        tag.putByte("easyarmorstands_tool", (byte) 1);
        item.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return ModdedItem.fromNative(item);
    }

    @Override
    public boolean isTool(Item item) {
        ItemStack itemStack = ModdedItem.toNative(item);
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            return customData.contains("easyarmorstands_tool");
        }
        return false;
    }
}
