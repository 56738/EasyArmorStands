package me.m56738.easyarmorstands.history;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.util.ArmorStandSnapshot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public class SpawnArmorStandAction implements HistoryAction {
    private UUID uuid;
    private ArmorStandSnapshot snapshot;

    private SpawnArmorStandAction(UUID uuid, ArmorStandSnapshot snapshot) {
        this.uuid = uuid;
        this.snapshot = snapshot;
    }

    public SpawnArmorStandAction(ArmorStand armorStand) {
        this(armorStand.getUniqueId(), new ArmorStandSnapshot(armorStand));
    }

    public ArmorStandSnapshot getSnapshot() {
        return snapshot;
    }

    @Override
    public void undo() {
        ArmorStand armorStand = Util.getArmorStand(uuid);
        if (armorStand == null) {
            throw new IllegalStateException();
        }
        snapshot = new ArmorStandSnapshot(armorStand);
        armorStand.remove();
    }

    @Override
    public void redo() {
        ArmorStand armorStand = snapshot.spawn();
        EasyArmorStands.getInstance().getHistoryManager().onEntityReplaced(uuid, armorStand.getUniqueId());
    }

    @Override
    public Component describe() {
        return Component.text("Spawn armor stand")
                .hoverEvent(Component.text()
                        .content("Position: ")
                        .append(Util.formatLocation(getSnapshot().getLocation()))
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
