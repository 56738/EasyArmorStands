package me.m56738.easyarmorstands.neoforge.listener;

import me.m56738.easyarmorstands.neoforge.EasyArmorStandsMod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

public class NeoForgeToolListener {
    public NeoForgeToolListener() {
        NeoForge.EVENT_BUS.addListener(FurnaceFuelBurnTimeEvent.class, this::onFurnaceFuelBurnTime);
    }

    private void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (EasyArmorStandsMod.isTool(event.getItemStack())) {
            event.setBurnTime(0);
        }
    }
}
