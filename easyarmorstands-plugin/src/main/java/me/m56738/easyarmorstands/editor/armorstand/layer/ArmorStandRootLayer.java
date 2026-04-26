package me.m56738.easyarmorstands.editor.armorstand.layer;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.layer.ElementLayer;
import me.m56738.easyarmorstands.api.editor.layer.ResettableLayer;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.node.ArmorStandNode;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class ArmorStandRootLayer extends ArmorStandLayer implements ElementLayer, ResettableLayer {
    private final Session session;
    private final ArmorStand entity;
    private final Component name;
    private ArmorStand skeleton;

    public ArmorStandRootLayer(Session session, ArmorStand entity, ArmorStandElement element) {
        super(session, session.properties(element), element);
        this.session = session;
        this.entity = entity;
        this.name = Message.component("easyarmorstands.node.select-bone");
        addNode(new ArmorStandNode(session, element));
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        if (skeleton != null) {
            updateSkeleton(skeleton);
        }
        context.setActionBar(name);
        context.addInput(new ReturnInput(session));
    }

    @Override
    public void onInactiveUpdate(@NotNull UpdateContext context) {
        super.onInactiveUpdate(context);
        if (skeleton != null) {
            updateSkeleton(skeleton);
        }
    }

    @Override
    public void onAdd(@NotNull AddContext context) {
        if (skeleton != null) {
            skeleton.remove();
        }

        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        if (!session.particleProvider().isVisibleThroughWalls()) {
            // Particles are not visible through walls. Spawn a glowing skeleton instead.
            skeleton = entity.getWorld().spawn(entity.getLocation(), ArmorStand.class, e -> {
                e.setPersistent(false);
                e.setVisibleByDefault(false);
                e.setVisible(false);
                e.setBasePlate(false);
                e.setArms(true);
                e.setGravity(false);
                e.setCanTick(false);
                e.setGlowing(true);
                updateSkeleton(e);
            });
            session.player().showEntity(plugin, skeleton);
        } else {
            skeleton = null;
        }
    }

    private void updateSkeleton(ArmorStand skeleton) {
        skeleton.teleport(entity.getLocation());
        skeleton.setSmall(entity.isSmall());
        for (ArmorStandPart part : ArmorStandPart.values()) {
            part.setPose(skeleton, part.getPose(entity));
        }
    }

    @Override
    public void onRemove(@NotNull RemoveContext context) {
        if (skeleton != null) {
            skeleton.remove();
        }
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = properties().get(EntityPropertyTypes.LOCATION);
        Location location = locationProperty.getValue();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            properties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        }
        properties().commit();
    }
}
