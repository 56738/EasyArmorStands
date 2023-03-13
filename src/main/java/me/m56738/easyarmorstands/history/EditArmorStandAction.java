package me.m56738.easyarmorstands.history;

import me.m56738.easyarmorstands.util.ArmorStandSnapshot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public class EditArmorStandAction implements HistoryAction {
    private ArmorStandSnapshot oldSnapshot;
    private ArmorStandSnapshot newSnapshot;
    private UUID uuid;

    public EditArmorStandAction(ArmorStandSnapshot oldSnapshot, ArmorStandSnapshot newSnapshot, UUID uuid) {
        this.oldSnapshot = oldSnapshot;
        this.newSnapshot = newSnapshot;
        this.uuid = uuid;
    }

    private ArmorStandSnapshot apply(ArmorStandSnapshot snapshot) {
        ArmorStand armorStand = Util.getArmorStand(uuid);
        if (armorStand == null) {
            throw new IllegalStateException();
        }
        ArmorStandSnapshot result = new ArmorStandSnapshot(armorStand);
        snapshot.apply(armorStand);
        return result;
    }

    @Override
    public void undo() {
        newSnapshot = apply(oldSnapshot);
    }

    @Override
    public void redo() {
        oldSnapshot = apply(newSnapshot);
    }

    @Override
    public Component describe() {
        return Component.text("Edit armor stand")
                .hoverEvent(Component.text()
                        .content("Old position: ")
                        .append(Util.formatLocation(oldSnapshot.getLocation()))
                        .append(Component.newline())
                        .append(Component.text("New position: "))
                        .append(Util.formatLocation(newSnapshot.getLocation()))
                        .color(NamedTextColor.GRAY)
                        .build());
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (uuid.equals(oldId)) {
            uuid = newId;
        }
    }
}
