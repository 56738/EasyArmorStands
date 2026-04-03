package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPartButton;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPositionButton;
import me.m56738.easyarmorstands.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class ArmorStandRootNode extends MenuNode implements ElementNode, ResettableNode {
    private final Session session;
    private final ArmorStand entity;
    private final ArmorStandElement element;
    private final ArmorStandPositionButton positionButton;
    private final EnumMap<ArmorStandPart, ArmorStandPartButton> partButtons = new EnumMap<>(ArmorStandPart.class);
    private final PropertyContainer container;
    private final Component name;
    private final boolean allowMenu;
    private ArmorStand skeleton;

    public ArmorStandRootNode(Session session, ArmorStand entity, ArmorStandElement element) {
        super(session);
        this.session = session;
        this.entity = entity;
        this.element = element;
        this.container = session.properties(element);
        this.name = Message.component("easyarmorstands.node.select-bone");
        this.allowMenu = session.player().hasPermission(Permissions.OPEN);

        for (ArmorStandPart part : ArmorStandPart.values()) {
            ArmorStandPartButton partButton = new ArmorStandPartButton(session, container, part, element);
            addButton(partButton);
            partButtons.put(part, partButton);
        }

        positionButton = new ArmorStandPositionButton(
                session,
                ParticleColor.WHITE,
                Message.component("easyarmorstands.node.position"),
                container,
                new ArmorStandOffsetProvider(element, container),
                element);
        addButton(positionButton);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        if (skeleton != null) {
            updateSkeleton(skeleton);
        }
        context.setActionBar(name);
        if (allowMenu) {
            context.addInput(new OpenElementMenuInput(session, element));
        }
        context.addInput(new ReturnInput(session));
    }

    @Override
    public void onInactiveUpdate(@NotNull UpdateContext context) {
        super.onInactiveUpdate(context);
        if (skeleton != null) {
            updateSkeleton(skeleton);
        }
    }

    public ArmorStandPositionButton getPositionButton() {
        return positionButton;
    }

    public ArmorStandPartButton getPartButton(ArmorStandPart part) {
        return partButtons.get(part);
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
    public @NotNull Element getElement() {
        return element;
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = container.get(EntityPropertyTypes.LOCATION);
        Location location = locationProperty.getValue();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            container.get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        }
        container.commit();
    }
}
