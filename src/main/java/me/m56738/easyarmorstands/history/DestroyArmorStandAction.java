package me.m56738.easyarmorstands.history;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

public class DestroyArmorStandAction extends SpawnArmorStandAction {
    public DestroyArmorStandAction(ArmorStand armorStand) {
        super(armorStand);
    }

    @Override
    public void undo() {
        super.redo();
    }

    @Override
    public void redo() {
        super.undo();
    }

    @Override
    public Component describe() {
        return Component.text("Destroy armor stand")
                .hoverEvent(Component.text()
                        .content("Position: ")
                        .append(Util.formatLocation(getSnapshot().getLocation()))
                        .color(NamedTextColor.GRAY)
                        .build());
    }
}
