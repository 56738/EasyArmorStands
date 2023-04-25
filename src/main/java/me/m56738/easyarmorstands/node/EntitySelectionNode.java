package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.session.EntityButtonProvider;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.*;

public class EntitySelectionNode extends MenuNode {
    private final Session session;
    private final Map<Entity, Button> buttons = new HashMap<>();
    private final List<EntityButtonProvider> providers = new ArrayList<>();

    public EntitySelectionNode(Session session, Component name) {
        super(session, name);
        this.session = session;
    }

    public void addProvider(EntityButtonProvider provider) {
        providers.add(provider);
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        Set<Entity> removed = new HashSet<>(buttons.keySet());
        double range = session.getRange();
        Player player = session.getPlayer();
        Location location = player.getLocation();
        for (Entity entity : location.getWorld().getNearbyEntities(location, range, range, range)) {
            if (entity.getLocation().distanceSquared(location) > range * range) {
                continue;
            }

            // entity exists, wasn't removed
            if (removed.remove(entity)) {
                continue;
            }

            // entity is new, create a button for it
            for (EntityButtonProvider provider : providers) {
                Button button = provider.createButton(session, entity);
                if (button != null) {
                    buttons.put(entity, button);
                    addButton(button);
                    break;
                }
            }
        }

        // remove buttons of entities which no longer exist
        for (Entity entity : removed) {
            removeButton(buttons.remove(entity));
        }

        super.onUpdate(eyes, target);
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (super.onClick(eyes, target, context)) {
            return true;
        }

        if (context.getType() == ClickType.RIGHT_CLICK) {
            Entity entity = context.getEntity();
            if (entity != null) {
                Button button = buttons.get(entity);
                if (button != null) {
                    session.pushNode(button.createNode());
                    return true;
                }
            }

            if (session.getPlayer().isSneaking()) {
                Player player = session.getPlayer();
                Location eyeLocation = player.getEyeLocation();
                Vector3d cursor = Util.getRotation(eyeLocation, new Matrix3d()).transform(0, 0, 2, new Vector3d());
                Vector3d position = new Vector3d(cursor);
                if (!player.isFlying()) {
                    position.y = 0;
                }
                position.add(Util.toVector3d(player.getLocation()));
                SessionManager sessionManager = EasyArmorStands.getInstance().getSessionManager();
                ArmorStand armorStand = sessionManager.spawn(player, position, eyeLocation.getYaw() + 180);
                if (armorStand == null) {
                    return false;
                }
                for (EntityButtonProvider provider : providers) {
                    Button button = provider.createButton(session, armorStand);
                    if (button != null) {
                        session.pushNode(button.createNode());
                        return true;
                    }
                }
                armorStand.remove();
                return false;
            }
        }

        return false;
    }
}
