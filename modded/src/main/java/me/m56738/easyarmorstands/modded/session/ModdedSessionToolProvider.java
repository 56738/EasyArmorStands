package me.m56738.easyarmorstands.modded.session;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;
import me.m56738.easyarmorstands.session.SessionToolProvider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;

import java.util.Locale;

public class ModdedSessionToolProvider implements SessionToolProvider {
    private static final String KEY = "easyarmorstands:tool";
    private final EasyArmorStandsCommon eas;

    public ModdedSessionToolProvider(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @Override
    public ItemStack createTool(Locale locale) {
        net.minecraft.world.item.ItemStack item = ModdedItemStack.toNative(eas.getConfiguration().editor.tool.render(locale));
        CustomData data = item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        tag.putBoolean(KEY, true);
        item.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return ModdedItemStack.fromNative((ModdedPlatform) eas.platform(), item);
    }

    @Override
    public boolean isTool(ItemStack item) {
        CustomData data = ModdedItemStack.toNative(item).get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            return data.copyTag().getBooleanOr(KEY, false);
        }
        return false;
    }
}
