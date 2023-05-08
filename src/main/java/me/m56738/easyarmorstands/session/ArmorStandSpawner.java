package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class ArmorStandSpawner implements EntitySpawner<ArmorStand> {
    private final Session session;

    public ArmorStandSpawner(Session session) {
        this.session = session;
    }

    @Override
    public ArmorStand spawn(Location location) {
        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        return spawnCapability.spawnEntity(location, ArmorStand.class, e -> {
            e.setGravity(false);
            for (ArmorStandPart part : ArmorStandPart.values()) {
                part.setPose(e, EulerAngle.ZERO);
            }
        });
    }

    @Override
    public Node createNode(ArmorStand entity) {
        return new ArmorStandRootNode(session, entity);
    }
}
